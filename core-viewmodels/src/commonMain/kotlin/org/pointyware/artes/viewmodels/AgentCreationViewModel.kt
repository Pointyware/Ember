package org.pointyware.artes.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.interactors.CreateAgentUseCase


/**
 * Presents a UI to edit a new or existing agent including:
 * - name
 * - description
 * - service
 */
interface AgentCreationViewModel {
    data class UiState(
        val agent: AgentInfoUiState = AgentInfoUiState.Empty,
        val hostOptions: List<HostUiState> = emptyList()
    )
    val state: StateFlow<UiState>

    /**
     * Triggered when the name input box text changes.
     */
    fun onNameChange(s: String)
    /**
     * Triggered when the description input box text changes.
     */
    fun onDescriptionChange(description: String)
    /**
     * Triggered when the user selects one of the services presented from [UiState.hostOptions].
     */
    fun onSelectService(id: Long)
    /**
     * Triggered when the user taps the save button.
     */
    fun onSaveAgent()
}

/**
 *
 */
class DefaultAgentCreationViewModel(
    private val createAgentUseCase: CreateAgentUseCase,
    private val viewModelScope: CoroutineScope
): AgentCreationViewModel {

    private val mutableState = MutableStateFlow(AgentCreationViewModel.UiState())
    override val state: StateFlow<AgentCreationViewModel.UiState>
        get() = mutableState

    override fun onNameChange(s: String) {
        mutableState.update { it.copy(agent = it.agent.copy(name = s)) }
    }

    override fun onDescriptionChange(description: String) {
        mutableState.update { it.copy(agent = it.agent.copy(model = description)) }
    }

    override fun onSelectService(id: Long) {
        mutableState.update {
            val selected = it.hostOptions.first { service -> service.id == id }
            it.copy(agent = it.agent.copy(service = selected))
        }
    }

    override fun onSaveAgent() {
        viewModelScope.launch {
            val agent = state.value.agent
            createAgentUseCase.invoke(
                name = agent.name,
                description = agent.model,
                serviceId = agent.service.id
            )
        }
    }
}
