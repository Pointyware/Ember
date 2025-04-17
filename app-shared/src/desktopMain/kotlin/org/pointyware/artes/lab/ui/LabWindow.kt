package org.pointyware.artes.lab.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import org.pointyware.artes.navigation.Destination
import org.pointyware.artes.text.completion.CompletionView
import org.pointyware.artes.text.completion.CompletionViewModel
import org.pointyware.artes.text.completion.CompletionViewState

/**
 * Allows a user to select a given service endpoint and experiment with configurations and inputs.
 *
 * TODO: integrate into UI hierarchy
 */
@Composable
fun LabWindow(
    completionViewModel: CompletionViewModel,
    onCloseRequest: () -> Unit,
    onDestinationChange: (Destination) -> Unit,
) {
    val state = rememberWindowState()
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = "Artes Lab"
    ) {
        val completionState by completionViewModel.state.collectAsState(CompletionViewModel.EMPTY)

        CompletionView(
            state = CompletionViewState(
                prompt = completionState.question,
                response = completionState.answer,
                error = completionState.error,
                isThinking = completionState.thinking
            ),
            onSubmit = completionViewModel::submit,
            onPromptChanged = completionViewModel::setQuestion
        )
    }
}
