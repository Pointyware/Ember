package org.pointyware.artes.text.completion

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.data.TextCompletionService

/**
 */
class CompletionViewModel(
    private val textCompletionService: TextCompletionService,
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val _state = MutableStateFlow(EMPTY)
    val state: StateFlow<UiState> = _state

    fun setQuestion(question: String) {
        _state.update { it.copy(question = question) }
    }

    fun submit(question: String) {
        // Clear the workspace and start thinking.
        _state.update { it.copy(thinking = true , error = null) }

        // Launch the question
        coroutineScope.launch(dispatcher) {
            try {
                val answer = textCompletionService.complete(question)
                _state.update { it.copy(answer = answer, thinking = false) }
            } catch (e: Exception) {
                _state.update { it.copy(answer = "", error = e.message, thinking = false) }
                e.printStackTrace()
            }
        }
    }

    data class UiState(
        val thinking: Boolean,
        val question: String,
        val answer: String,
        val error: String?
    )

    companion object {
        val EMPTY = UiState(false, "", "", null)
    }
}
