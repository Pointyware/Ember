package org.pointyware.artes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.entities.Agent
import org.pointyware.artes.interactors.BeginAgentCreationJourney
import org.pointyware.artes.interactors.GetAgentListUseCase

/**
 * Must call [onInit] to start loading the agent list.
 */
interface AgentListViewModel {

    val state: StateFlow<AgentListUiState>

    /**
     * Triggered when the user taps on an agent in the list.
     */
    fun onInit()

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
): ViewModel(), AgentListViewModel {

    private val mutableState = MutableStateFlow(AgentListUiState())
    override val state: StateFlow<AgentListUiState>
        get() = mutableState

    override fun onInit() {
        viewModelScope.launch {
            mutableState.update {
                it.copy(
                    loading = LoadingUiState.Loading,
                )
            }
            getAgentListUseCase.invoke()
                .onSuccess { agentList ->
                    val agentInfoList = agentList.map(Agent::toUiState)
                    mutableState.update {
                        it.copy(
                            agents = agentInfoList,
                            loading = LoadingUiState.Idle,
                        )
                    }
                }
                .onFailure {  error ->
                    mutableState.update {
                        it.copy(
                            loading = LoadingUiState.Error(error.message ?: "Unknown error"),
                        )
                    }
                }
        }
    }

    override fun onTapAgent(id: Long) {
        viewModelScope.launch {
            TODO("Navigate to agent details screen")
        }
    }


    override fun onCreateAgent() {
        viewModelScope.launch {
            beginAgentCreationJourney.invoke()
        }
    }
}
