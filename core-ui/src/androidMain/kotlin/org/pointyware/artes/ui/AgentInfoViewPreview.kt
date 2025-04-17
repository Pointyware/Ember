package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.viewmodels.HostUiState

/**
 *
 */
@Preview
@Composable
fun AgentInfoViewPreview() {
    AgentInfoView(
        state = AgentInfoViewState(
            name = "Some Agent",
            description = "An agent",
            service = HostUiState(
                id = 0L,
                title = "OpenAI Dev",
                host = OpenAi
            )
        ),
        onEdit = {},
        onDelete = {}
    )
}
