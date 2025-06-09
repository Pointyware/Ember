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
    val state: StateFlow<AgentInfoUiState>

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
    private val mutableState = MutableStateFlow(AgentInfoUiState.Empty)
    override val state: StateFlow<AgentInfoUiState>
        get() = mutableState

    override fun onEdit() {
        viewModelScope.launch {
            beginEditingAgentUseCase.invoke(state.value.id)
        }
    }

    override fun onDelete() {
        viewModelScope.launch {
            removeAgentUseCase.invoke(state.value.id)
        }
    }
}
