package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.ember.training.viewmodels.TrainingUiState
import org.pointyware.ember.training.viewmodels.TrainingViewModel
import org.pointyware.ember.ui.NeuralNetworkView

/**
 * The Training Screen
 */
@Composable
fun TrainingScreen(
    viewModel: TrainingViewModel,
) {
    val state by viewModel.state.collectAsState()
    TrainingView(
        state = state,
        onStart = viewModel::onStart,
        onStop = viewModel::onStop,
        onReset = viewModel::onReset,
        onRun = viewModel::onRun,
    )
}

/**
 * This view displays a network under training, and provides controls to start, stop, reset,
 * and run a set number of epochs.
 */
@Composable
fun TrainingView(
    state: TrainingUiState,
    onStart: ()-> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit,
    onRun: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Column {

            Text(
                text = "Training: ${if (state.isTraining) "In Progress" else "Not Started"}"
            )
            Text(
                text = "Elapsed Epochs: ${state.epochsTrained}"
            )
            Button(
                onClick = onStart
            ) {
                Text(
                    text = "Start"
                )
            }
            Button(
                onClick = onStop
            ) {
                Text(
                    text = "Stop"
                )
            }
            Button(
                onClick = onReset
            ) {
                Text(
                    text = "Reset"
                )
            }
            var epochsRemaining by remember(state.epochsRemaining) { mutableStateOf(state.epochsRemaining) }
            TextField(
                value = epochsRemaining.toString(),
                onValueChange = { newValue ->
                    epochsRemaining = newValue.toIntOrNull() ?: 0
                },
                label = { Text("Epochs Remaining") },
            )
            Button(
                onClick = { onRun(epochsRemaining) }
            ) {
                Text(
                    text = "Run"
                )
            }
        }

        NeuralNetworkView(
            state = state.networkState
        )
    }
}
