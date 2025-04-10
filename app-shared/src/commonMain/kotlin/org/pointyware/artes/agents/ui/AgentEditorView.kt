package org.pointyware.artes.agents.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.artes.ui.components.OptionSelector
import org.pointyware.artes.ui.components.rememberOptionSelectorState
import org.pointyware.artes.viewmodels.AgentEditorUiState

/**
 *
 */
@Composable
fun AgentEditorView(
    state: AgentEditorUiState,
    modifier: Modifier = Modifier,
    onSelectHost: (Int)->Unit,
    onSubmit: (String, Long, String)->Unit,
) {
    Column(
        modifier = modifier
    ) {
        var agentName by remember { mutableStateOf(state.agentName)}
        TextField(
            value = agentName,
            onValueChange = { agentName = it },
            label = { Text("Agent Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OptionSelector(
            state = rememberOptionSelectorState(state.hosts.map { it.title }, state.selectedHost),
            onOptionSelected = onSelectHost,
        )

        var selectedModel by remember { mutableStateOf(state.selectedModel) }
        OptionSelector(
            state = rememberOptionSelectorState(state.hostModels.map { it.name }, selectedModel),
            onOptionSelected = { selectedModel = it },
        )

        var instructions by remember { mutableStateOf(state.instructions) }
        TextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("System Instructions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = agentName.isNotBlank() && state.selectedHost != null && selectedModel != null,
            onClick = { onSubmit(agentName, selectedModel?.let { state.hostModels[it].id } ?: return@Button, instructions) }
        ) {
            Text("Submit")
        }
    }
}
