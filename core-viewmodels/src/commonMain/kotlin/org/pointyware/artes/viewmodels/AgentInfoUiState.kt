package org.pointyware.artes.viewmodels
/**
 * General information about an agent to display in a detailed view.
 *
 * @see [org.pointyware.artes.entities.Agent]
 */
data class AgentInfoUiState(
    val id: String,
    val name: String,
    val service: HostUiState,
    val model: String
) {
    companion object {
        val Empty = AgentInfoUiState(
            id = "",
            name = "",
            service = HostUiState(
                id = 0L,
                title = ""
            ),
            model = ""
        )
    }
}
