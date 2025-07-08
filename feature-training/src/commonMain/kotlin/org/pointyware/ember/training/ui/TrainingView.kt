package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.pointyware.ember.training.viewmodels.TrainingUiState
import org.pointyware.ember.ui.NeuralNetworkView

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
    Column {
    }
    Column {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            NeuralNetworkView(
                state = state.networkState
            )

            ObjectiveGraph(
                objectiveCeiling = state.statistics.ceiling,
                objectiveLabel = state.statistics.objectiveName,
                epochCount = state.statistics.epochCount,
                data = state.statistics.data,
                modifier = Modifier.weight(1f)
            )
        }

        // Controls:
        Row {
            Text(
                text = "Training: ${if (state.isTraining) "In Progress" else "Not Started"}"
            )
            Text(
                text = "Elapsed Epochs: ${state.epochsTrained}"
            )
        }
        Row(
            modifier = modifier
        ) {
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
    }
}
