package org.pointyware.artes.viewmodels
/**
 * General information about an agent to display in a detailed view.
 *
 * @see [org.pointyware.artes.core.entities.Agent]
 */
data class AgentInfoUiState(
    val id: String,
    val name: String,
    val service: ServiceInfoUiState,
    val model: String
) {
    companion object {
        val Empty = AgentInfoUiState(
            id = "",
            name = "",
            service = ServiceInfoUiState(
                id = "",
                title = "",
                url = ""
            ),
            model = ""
        )
    }
}
