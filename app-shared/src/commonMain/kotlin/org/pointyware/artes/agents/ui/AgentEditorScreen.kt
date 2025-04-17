package org.pointyware.artes.agents.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.agents.viewmodels.AgentEditorViewModel

/**
 *
 */
@Composable
fun AgentEditorScreen(
    viewModel: AgentEditorViewModel = remember { getKoin().get<AgentEditorViewModel>() },
) {
    LaunchedEffect(Unit) {
        viewModel.loadHosts()
    }
    val state by viewModel.state.collectAsState()
    AgentEditorView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onSelectHost = viewModel::onSelectHost,
        onSubmit = viewModel::onSave,
    )

    val alert: String? by viewModel.alert.collectAsState(null)
    var dialogState by remember(alert) { mutableStateOf(alert) }
    val safeDialogState = dialogState
    if (safeDialogState != null) {
        Dialog(
            onDismissRequest = { dialogState = null },
        ) {
            Text(safeDialogState)
        }
    }
}
