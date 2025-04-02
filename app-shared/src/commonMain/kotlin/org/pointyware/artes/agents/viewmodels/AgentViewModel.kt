package org.pointyware.artes.agents.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.hosts.viewmodels.mapToUiState
import org.pointyware.artes.interactors.CreateAgentUseCase
import org.pointyware.artes.interactors.agents.GetAgentUseCase
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase

/**
 * Manages the UI state and responds to events for an Agent View.
 */
class AgentViewModel(
    private val getHostsUseCase: GetHostServicesUseCase,
    // TODO: add use cases/repositories
    private val createAgentUseCase: CreateAgentUseCase,
    private val getAgentUseCase: GetAgentUseCase,
): ViewModel() {


    private val mutableAlert = MutableSharedFlow<String>()
    val alert: SharedFlow<String> get() = mutableAlert.asSharedFlow()

    private val mutableState = MutableStateFlow(AgentUiState.Empty)
    val state: StateFlow<AgentUiState>
        get() = mutableState.asStateFlow()

    fun loadAgent(agentId: Long) {
        TODO("Not yet implemented")
    }

    fun loadHosts() {
        viewModelScope.launch {
            getHostsUseCase()
                .onSuccess { hostList ->
                    mutableState.update {
                        it.copy(
                            hosts = hostList.map(::mapToUiState)
                        )
                    }
                }
                .onFailure {
                    mutableAlert.emit(it.message ?: "Unknown error")
                }
        }
    }

    fun onSelectHost(index: Int) {
        TODO("Not yet implemented")
    }

    fun onSave(title: String, hostId: Int, modelId: Int, instructions: String) {
        TODO("Not yet implemented")
    }
}
