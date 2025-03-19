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
                    id = "0",
                    name = "Suzy",
                    service = "OpenAi",
                    model = "gpt-3.5-turbo",
                ),
                AgentListItemState(
                    id = "1",
                    name = "Ranger",
                    service = "VertexAi",
                    model = "chat-bison",
                ),
                AgentListItemState(
                    id = "2",
                    name = "Copilot",
                    service = "VertexAi",
                    model = "code-gecko",
                ),
                AgentListItemState(
                    id = "3",
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
