package org.pointyware.artes.viewmodels

/**
 * Represents the UI state of the Agent View for creating new agents and modifying existing ones.
 */
data class AgentEditorUiState(
    val agentName: String,
    val hosts: List<HostUiState>,
    val selectedHost: Int?,
    val hostModels: List<ModelUiState>,
    val selectedModel: Int?,
    val instructions: String,
) {
    companion object {
        val Empty = AgentEditorUiState(
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
