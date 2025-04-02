package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 */
@Preview
@Composable
fun AgentCreationViewPreview() {
    AgentCreationView(
        state = AgentCreationViewState(
            name = "Agent Name",
            description = "Agent Description",
            serviceSelectionState = ServiceSelectionDropDownState(
                services = listOf(
                    ServiceListItemState(
                        id = 0L,
                        label = "Google",
                        service = "VertexAI"
                    ),
                    ServiceListItemState(
                        id = 1L,
                        label = "OpenAI Stage",
                        service = "OpenAI"
                    ),
                    ServiceListItemState(
                        id = 2L,
                        label = "OpenAI Prod",
                        service = "OpenAI"
                    )
                ),
                selectedIndex = 0
            )
        ),
    )
}
