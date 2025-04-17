package org.pointyware.artes.hosts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pointyware.artes.hosts.viewmodels.ExtraOptionsUiState
import org.pointyware.artes.hosts.viewmodels.HostConfigUiState

/**
 *
 */
@Composable
fun HostEditorView(
    state: HostConfigUiState,
    modifier: Modifier = Modifier,
    onCreateHost: (String, ExtraOptionsUiState) -> Unit
) {
    var hostName by remember(state.title) { mutableStateOf(state.title) }
    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = hostName,
            onValueChange = { hostName = it },
            label = { Text("Host Name") },
            modifier = Modifier.fillMaxWidth()
        )
        var extraOptionsState by remember(state.extraOptions) { mutableStateOf(state.extraOptions) }
        when (val capture = extraOptionsState) {
            is ExtraOptionsUiState.OpenAi -> {
                TextField(
                    value = capture.orgId,
                    onValueChange = { extraOptionsState = capture.copy(orgId = it) },
                    label = { Text("Organization Id") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = capture.apiKey,
                    onValueChange = { extraOptionsState = capture.copy(apiKey = it) },
                    label = { Text("API Key") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is ExtraOptionsUiState.Gemini -> {
                Text("Gemini options are not yet implemented")
            }
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                onCreateHost(hostName, extraOptionsState)
            }
        ) {
            Text("Create Host")
        }
    }
}
