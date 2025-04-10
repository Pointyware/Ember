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
import org.pointyware.artes.interactors.CreateAgentUseCase
import org.pointyware.artes.interactors.GetServiceModelsUseCase
import org.pointyware.artes.interactors.agents.GetAgentUseCase
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase
import org.pointyware.artes.viewmodels.AgentEditorUiState
import org.pointyware.artes.viewmodels.toUiState

/**
 * Manages the UI state and responds to events for an Agent View.
 */
class AgentEditorViewModel(
    private val getAvailableHostsUseCase: GetHostServicesUseCase,
    private val getServiceModelsUseCase: GetServiceModelsUseCase,
    private val createAgentUseCase: CreateAgentUseCase,
    private val getAgentUseCase: GetAgentUseCase,
): ViewModel() {

    private val mutableAlert = MutableSharedFlow<String>()
    val alert: SharedFlow<String> get() = mutableAlert.asSharedFlow()

    private val mutableState = MutableStateFlow(AgentEditorUiState.Empty)
    val state: StateFlow<AgentEditorUiState>
        get() = mutableState.asStateFlow()

    fun loadAgent(agentId: Long) {
        TODO("Not yet implemented")
    }

    fun loadHosts() {
        viewModelScope.launch {
            getAvailableHostsUseCase()
                .onSuccess { hostList ->
                    mutableState.update {
                        it.copy(
                            hosts = hostList.map { host -> host.toUiState() }
                        )
                    }
                }
                .onFailure {
                    mutableAlert.emit(it.message ?: "Unknown error")
                }
        }
    }

    fun onSelectHost(index: Int) {
        mutableState.update {
            it.copy(selectedHost = index)
        }
        viewModelScope.launch {
            getServiceModelsUseCase(index)
                .onSuccess { models ->
                    mutableState.update {
                        it.copy(
                            selectedHost = index,
                            hostModels = models.map { model -> model.toUiState() }
                        )
                    }
                }
                .onFailure {
                    mutableAlert.emit(it.message ?: "Unknown error")
                }
        }
    }

    fun onSave(title: String, modelId: Long, instructions: String) {
        viewModelScope.launch {
            createAgentUseCase(title, modelId, instructions)
        }
    }
}
