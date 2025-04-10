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
                    host = "OpenAi",
                    model = "gpt-3.5-turbo",
                ),
                AgentListItemState(
                    id = 1L,
                    name = "Ranger",
                    host = "VertexAi",
                    model = "chat-bison",
                ),
                AgentListItemState(
                    id = 2L,
                    name = "Copilot",
                    host = "VertexAi",
                    model = "code-gecko",
                ),
                AgentListItemState(
                    id = 3L,
                    name = "Preview",
                    host = "OpenAi",
                    model = "gpt-4-preview",
                )
            )
        ),
        onCreateAgent = {},
        onSelectAgent = {}
    )
}
