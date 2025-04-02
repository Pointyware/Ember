package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun AgentListViewPreview() {
    AgentListView(
        state = AgentListViewState(
            agents = listOf(
                AgentListItemState(
                    id = 0L,
                    name = "Suzy",
                    service = "OpenAi",
                    model = "gpt-3.5-turbo",
                ),
                AgentListItemState(
                    id = 1L,
                    name = "Ranger",
                    service = "VertexAi",
                    model = "chat-bison",
                ),
                AgentListItemState(
                    id = 2L,
                    name = "Copilot",
                    service = "VertexAi",
                    model = "code-gecko",
                ),
                AgentListItemState(
                    id = 3L,
                    name = "Preview",
                    service = "OpenAi",
                    model = "gpt-4-preview",
                )
            )
        ),
        onCreateAgent = {},
        onSelectAgent = {}
    )
}
