package org.pointyware.artes.agents.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages the UI state and responds to events for an Agent View.
 */
class AgentViewModel(
    // TODO: add use cases/repositories
): ViewModel() {

    private val mutableState = MutableStateFlow(AgentUiState.Empty)
    val state: StateFlow<AgentUiState>
        get() = mutableState.asStateFlow()

    fun loadAgent(agentId: Long) {
        TODO("Not yet implemented")
    }

    fun loadHosts() {
        TODO("Not yet implemented")
    }

    fun onSelectHost(index: Int) {
        TODO("Not yet implemented")
    }

    fun onSave(title: String, hostId: Int, modelId: Int, instructions: String) {
        TODO("Not yet implemented")
    }
}
