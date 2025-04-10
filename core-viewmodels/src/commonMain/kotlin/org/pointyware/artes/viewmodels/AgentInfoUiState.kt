package org.pointyware.artes.viewmodels
/**
 * General information about an agent to display in a detailed view.
 *
 * Compare with [AgentUiState].
 *
 * @see [org.pointyware.artes.entities.Agent]
 */
data class AgentInfoUiState(
    val id: Long,
    val name: String,
    val host: HostUiState,
    val model: ModelUiState
) {
    companion object {
        val Empty = AgentInfoUiState(
            id = -1L,
            name = "",
            host = HostUiState(
                id = 0L,
                title = ""
            ),
            model = ModelUiState(
                id = 0L,
                name = ""
            )
        )
    }
}
