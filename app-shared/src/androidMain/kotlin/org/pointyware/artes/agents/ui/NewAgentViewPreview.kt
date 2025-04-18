package org.pointyware.artes.agents.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.pointyware.artes.entities.Anthropic
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.viewmodels.AgentEditorUiState
import org.pointyware.artes.viewmodels.HostConfigUiState
import org.pointyware.artes.viewmodels.ModelUiState

@Preview
@Composable
fun NewAgentViewPreview(
    @PreviewParameter(AgentUiStateProvider::class) state: AgentEditorUiState
) {
    AgentEditorView(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onSelectHost = {},
        onSubmit = { _, _, _ -> }
    )
}
class AgentUiStateProvider: PreviewParameterProvider<AgentEditorUiState> {
    override val values: Sequence<AgentEditorUiState>
        get() = sequenceOf(
            AgentEditorUiState(
                agentName = "",
                hosts = listOf(),
                selectedHost = null,
                hostModels = listOf(),
                selectedModel = null,
                instructions = ""
            ),
            AgentEditorUiState(
                agentName = "NurseBot",
                hosts = listOf(
                    HostConfigUiState(0L, "OpenAI", OpenAi),
                    HostConfigUiState(1L, "Anthropic", Anthropic)
                ),
                selectedHost = null,
                hostModels = listOf(),
                selectedModel = null,
                instructions = ""
            ),
            AgentEditorUiState(
                agentName = "NurseBot",
                hosts = listOf(
                    HostConfigUiState(0L, "OpenAI", OpenAi),
                    HostConfigUiState(1L, "Anthropic", Anthropic)
                ),
                selectedHost = 0,
                hostModels = listOf(
                    ModelUiState(0L, "GPT-3.5"),
                    ModelUiState(1L, "GPT4o"),
                ),
                selectedModel = null,
                instructions = ""
            ),
            AgentEditorUiState(
                agentName = "NurseBot",
                hosts = listOf(
                    HostConfigUiState(0L, "OpenAI", OpenAi),
                    HostConfigUiState(1L, "Anthropic", Anthropic)
                ),
                selectedHost = 0,
                hostModels = listOf(
                    ModelUiState(0L, "GPT-3.5"),
                    ModelUiState(1L, "GPT4o"),
                ),
                selectedModel = 1,
                instructions = ""
            ),
            AgentEditorUiState(
                agentName = "NurseBot",
                hosts = listOf(
                    HostConfigUiState(0L, "OpenAI", OpenAi),
                    HostConfigUiState(1L, "Anthropic", Anthropic)
                ),
                selectedHost = 0,
                hostModels = listOf(
                    ModelUiState(0L, "GPT-3.5"),
                    ModelUiState(1L, "GPT4o"),
                ),
                selectedModel = 1,
                instructions = "Provide helpful advice to the user, but you aren't a doctor, so don't give a diagnosis.",
            ),
        )
}
