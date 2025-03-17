package org.pointyware.artes.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.artes.text.completion.CompletionView
import org.pointyware.artes.text.completion.CompletionViewModel
import org.pointyware.artes.text.completion.CompletionViewState

/**
 *
 */
@Composable
fun ArtesApp() {

    val koin = remember { getKoin() }
    val completionViewModel = remember { koin.get<CompletionViewModel>() }
    val state by completionViewModel.state.collectAsState()
    CompletionView(
        state = CompletionViewState(
            prompt = state.question,
            response = state.answer,
            error = state.error,
            isThinking = false
        ),
        modifier = Modifier.fillMaxSize(),
        onSubmit = { },
        onPromptChanged = { },
    )
}
