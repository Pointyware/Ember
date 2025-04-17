package org.pointyware.artes.shared.workbench

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import org.pointyware.artes.data.SettingsRepository
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.shared.settings.SettingsWindow
import org.pointyware.artes.shared.viewmodels.SettingsViewModel

/**
 * Allows a user to
 * 1. select AI models based on input/output
 * 2. configure parameters for a model and save as a workflow
 * 3. drag-and-drop saved workflows into the workspace and connect into process architectures
 * 4. test/run saved architectures
 */
@Composable
fun WorkbenchWindow(
    onCloseRequest: () -> Unit,
    onDestinationChange: (Destination)->Unit,
) {
    var isSettingsOpen by remember { mutableStateOf(false) }
    val windowState = rememberWindowState()
    Window(
        title = "Artes: Skillful Helpers",
        onCloseRequest = onCloseRequest,
        state = windowState
    ) {
        WorkbenchMenuBar(
            onOpenSettings = { isSettingsOpen = true },
            onOpenDashboard = { onDestinationChange(Destination.Dashboard) },
        )

        Row {
            Sidebar(
                modifier = Modifier.fillMaxWidth(0.3f)
            )
            Spacer(
                modifier = Modifier.fillMaxHeight()
                    .width(8.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Workspace()
        }
    }

    if (isSettingsOpen) {
        val viewModelScope = rememberCoroutineScope()
        val settingsViewModel = remember {
            val repository: SettingsRepository = TODO("Implement common settings repo") // JvmSettingsRepository(FileDataSource(File("settings.txt")))
            SettingsViewModel(
                settingsRepository = repository,
                coroutineScope = viewModelScope
            )
        }
        SettingsWindow(
            settingsViewModel = settingsViewModel,
            onCloseRequest = { isSettingsOpen = false }
        )
    }
}
