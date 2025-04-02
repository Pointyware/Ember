package org.pointyware.artes.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.entities.Agent
import org.pointyware.artes.interactors.BeginAgentCreationJourney
import org.pointyware.artes.interactors.GetAgentListUseCase

/**
 *
 */
interface AgentListViewModel {

    val state: StateFlow<UiState>

    data class UiState(
        val agents: List<AgentInfoUiState> = emptyList(),
        val selectedAgent: Int? = null
    )

    /**
     * Triggered when a user taps on an agent in the list.
     */
    fun onTapAgent(id: Long)

    /**
     * Triggered when a user taps the new agent button.
     */
    fun onCreateAgent()
}

/**
 *
 */
class DefaultAgentListViewModel(
    private val getAgentListUseCase: GetAgentListUseCase,
    private val beginAgentCreationJourney: BeginAgentCreationJourney,
    private val viewModelScope: CoroutineScope
): AgentListViewModel {

    private val mutableState = MutableStateFlow(AgentListViewModel.UiState())
    override val state: StateFlow<AgentListViewModel.UiState>
        get() = mutableState

    init {
        viewModelScope.launch {
            getAgentListUseCase.invoke().collect { agentList ->
                val agentInfoList = agentList.map(Agent::toUiState)
                mutableState.update { uiState ->
                    uiState.copy(
                        agents = agentInfoList,
                        selectedAgent = uiState.selectedAgent?.takeIf { it < agentInfoList.size }
                    )
                }
            }
        }
    }

    override fun onTapAgent(id: Long) {
        viewModelScope.launch {
            mutableState.update { uiState ->
                uiState.copy(selectedAgent = uiState.agents.indexOfFirst { it.id == id })
            }
        }
    }


    override fun onCreateAgent() {
        viewModelScope.launch {
            beginAgentCreationJourney.invoke()
        }
    }
}
