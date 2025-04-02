package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

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
            service = ServiceListItemState(
                id = 0L,
                label = "OpenAI Dev",
                service = "OpenAI"
            )
        ),
        onEdit = {},
        onDelete = {}
    )
}
