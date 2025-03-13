package org.pointyware.artes.text.completion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun rememberCompletionViewState(
    prompt: String = "",
    response: String = "",
    error: String? = null,
    isThinking: Boolean = false
) = remember(prompt, response, error, isThinking) {
    CompletionViewState(
        prompt = prompt,
        response = response,
        error = error,
        isThinking = isThinking
    )
}

data class CompletionViewState(
    val prompt: String,
    val response: String,
    val error: String?,
    val isThinking: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletionView(
    state: CompletionViewState = rememberCompletionViewState(),
    modifier: Modifier = Modifier,
    onPromptChanged: (String)->Unit,
    onSubmit: (String)->Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            value = state.prompt,
            onValueChange = onPromptChanged,
            label = { Text("Question") },
            enabled = state.isThinking.not(),
        )
        Button(
            onClick = { onSubmit(state.prompt) },
            enabled = !state.isThinking,
        ) {
            Text("Submit")
        }

        Text("Answer: ")
        Text(state.response)
        if (state.error != null) {
            Text("Error: ${state.error}", color = Color.Red)
        }
    }
}
