package org.pointyware.artes.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.pointyware.artes.interactors.BeginEditingAgentUseCase
import org.pointyware.artes.interactors.RemoveAgentUseCase

/**
 * View Model of a UI the displays agent information and allows the user to open the editor or
 * remove this agent.
 */
interface AgentInfoViewModel {
    data class UiState (
        val info: AgentInfoUiState
    )
    val state: StateFlow<UiState>

    /**
     * Triggered when the user wants to edit this agent.
     */
    fun onEdit()

    /**
     * Triggered when the user wants to delete this agent.
     */
    fun onDelete()
}

/**
 *
 */
class DefaultAgentInfoViewModel(
    private val beginEditingAgentUseCase: BeginEditingAgentUseCase,
    private val removeAgentUseCase: RemoveAgentUseCase,
    private val viewModelScope: CoroutineScope
): ViewModel(), AgentInfoViewModel {
    private val mutableState = MutableStateFlow(AgentInfoViewModel.UiState(AgentInfoUiState.Empty))
    override val state: StateFlow<AgentInfoViewModel.UiState>
        get() = mutableState

    override fun onEdit() {
        viewModelScope.launch {
            beginEditingAgentUseCase.invoke(state.value.info.id)
        }
    }

    override fun onDelete() {
        viewModelScope.launch {
            removeAgentUseCase.invoke(state.value.info.id)
        }
    }
}
