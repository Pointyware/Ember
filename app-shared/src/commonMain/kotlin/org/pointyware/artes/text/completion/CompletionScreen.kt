package org.pointyware.artes.text.completion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin

/**
 *
 */
@Composable
fun CompletionScreen(
    onBack: ()->Unit,
) {
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
        onSubmit = completionViewModel::submit,
        onPromptChanged = completionViewModel::setQuestion,
    )
}
