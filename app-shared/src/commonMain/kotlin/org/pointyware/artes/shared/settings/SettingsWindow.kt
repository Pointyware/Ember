package org.pointyware.artes.shared.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import org.pointyware.artes.shared.viewmodels.SettingsViewModel

/**
 *
 */
@Composable
fun SettingsWindow(
    settingsViewModel: SettingsViewModel,
    onCloseRequest: () -> Unit,
) {
    val state by settingsViewModel.state.collectAsState()
    Window(
        onCloseRequest = onCloseRequest,
        title = "Settings",
    ) {
        Column {
            TextField(
                value = state.openAiCredentials?.apiKey ?: "",
                onValueChange = { settingsViewModel.setOpenAiKey(it) },
                label = { Text("OpenAI API Key") }
            )
            TextField(
                enabled = state.openAiCredentials?.apiKey != null,
                value = state.openAiCredentials?.orgId ?: "",
                onValueChange = { settingsViewModel.setOpenAiOrganization(it) },
                label = { Text("OpenAI Organization ID") }
            )
            Row {
                Button(
                    onClick = { settingsViewModel.clearOpenAiCredentials() }
                ) {
                    Text(text = "Clear")
                }
                Button(
                    enabled = state.openAiCredentials?.apiKey != null,
                    onClick = { settingsViewModel.saveCredentials() }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
