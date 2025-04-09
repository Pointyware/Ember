package org.pointyware.artes.agents.viewmodels

import org.pointyware.artes.viewmodels.HostUiState
import org.pointyware.artes.viewmodels.ModelUiState

/**
 * Represents the UI state of the Agent View for creating new agents and modifying existing ones.
 */
data class AgentUiState(
    val agentName: String,
    val hosts: List<HostUiState>,
    val selectedHost: Int?,
    val hostModels: List<ModelUiState>,
    val selectedModel: Int?,
    val instructions: String,
) {
    companion object {
        val Empty = AgentUiState(
            agentName = "",
            hosts = emptyList(

            ),
            selectedHost = null,
            hostModels = emptyList(),
            selectedModel = null,
            instructions = "",
        )
    }
}
