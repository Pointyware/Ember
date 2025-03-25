package org.pointyware.artes.agents.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Preview
@Composable
fun NewAgentViewPreview(
    @PreviewParameter(NewAgentViewStateProvider::class) state: NewAgentViewState
) {
    NewAgentView(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onSelectHost = {},
        onSubmit = { _, _, _, _ -> }
    )
}
class NewAgentViewStateProvider: PreviewParameterProvider<NewAgentViewState> {
    override val values: Sequence<NewAgentViewState>
        get() = sequenceOf(
            NewAgentViewState(
                agentName = "",
                hosts = listOf(),
                selectedHost = null,
                hostModels = listOf(),
                selectedModel = null,
                instructions = ""
            ),
            NewAgentViewState(
                agentName = "NurseBot",
                hosts = listOf("OpenAI", "Anthropic"),
                selectedHost = null,
                hostModels = listOf(),
                selectedModel = null,
                instructions = ""
            ),
            NewAgentViewState(
                agentName = "NurseBot",
                hosts = listOf("OpenAI", "Anthropic"),
                selectedHost = 0,
                hostModels = listOf("GPT-3.5", "GPT4o"),
                selectedModel = null,
                instructions = ""
            ),
            NewAgentViewState(
                agentName = "NurseBot",
                hosts = listOf("OpenAI", "Anthropic"),
                selectedHost = 0,
                hostModels = listOf("GPT-3.5", "GPT4o"),
                selectedModel = 1,
                instructions = ""
            ),
            NewAgentViewState(
                agentName = "NurseBot",
                hosts = listOf("OpenAI", "Anthropic"),
                selectedHost = 0,
                hostModels = listOf("GPT-3.5", "GPT4o"),
                selectedModel = 1,
                instructions = "Provide helpful advice to the user, but you aren't a doctor, so don't give a diagnosis.",
            ),
        )
}
