package org.pointyware.artes.agents.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.artes.entities.HostConfig
import org.pointyware.artes.interactors.CreateAgentUseCase
import org.pointyware.artes.interactors.GetServiceModelsUseCase
import org.pointyware.artes.interactors.agents.GetAgentUseCase
import org.pointyware.artes.interactors.hosts.GetHostServicesUseCase
import org.pointyware.artes.viewmodels.AgentEditorUiState
import org.pointyware.artes.viewmodels.toUiState

/**
 * Manages the UI state and responds to events for an Agent View.
 *
 * Must call [loadHosts] to start loading the host list.
 */
class AgentEditorViewModel(
    private val getAvailableHostsUseCase: GetHostServicesUseCase,
    private val getServiceModelsUseCase: GetServiceModelsUseCase,
    private val createAgentUseCase: CreateAgentUseCase,
    private val getAgentUseCase: GetAgentUseCase,
): ViewModel() {

    private val _onBack = Channel<Unit>(1)
    val onBack: Flow<Unit> get() = _onBack.consumeAsFlow()

    private val mutableAlert = MutableSharedFlow<String>()
    val alert: SharedFlow<String> get() = mutableAlert.asSharedFlow()

    private val mutableState = MutableStateFlow(AgentEditorUiState.Empty)
    val state: StateFlow<AgentEditorUiState>
        get() = mutableState.asStateFlow()

    fun loadAgent(agentId: Long) {
        viewModelScope.launch {
            getAgentUseCase.invoke(agentId)
                .onSuccess {
                    TODO("Handle agent loading")
                }
                .onFailure {
                    mutableAlert.emit(it.message ?: "Unknown error")
                }
        }
    }

    fun loadHosts() {
        viewModelScope.launch {
            getAvailableHostsUseCase()
                .onSuccess { hostList ->
                    mutableState.update {
                        it.copy(
                            hosts = hostList.map(HostConfig::toUiState)
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
                    it.printStackTrace()
                    mutableAlert.emit(it.message ?: "Unknown error")
                }
        }
    }

    fun onSave(title: String, modelId: Long, instructions: String) {
        viewModelScope.launch {
            createAgentUseCase(title, modelId, instructions)
            _onBack.send(Unit)
        }
    }
}
