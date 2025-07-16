package org.pointyware.ember.training.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        LazyRow(
            modifier = Modifier.weight(1f)
        ) {
            items(state.networks) { network ->
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    NeuralNetworkView(
                        state = network.networkState
                    )

                    ObjectiveGraph(
                        objectiveFloor = network.statistics.floor,
                        objectiveCeiling = network.statistics.ceiling,
                        objectiveLabel = network.statistics.data.joinToString { it.label },
                        epochCount = network.statistics.epochCount,
                        data = network.statistics.data,
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
        }

        // Controls:
        Row {
            Text(
                text = "Training: ${if (state.isTraining) "In Progress" else "Not Started"}"
            )
            val elapsed = state.epochsElapsed
            Text(
                text = "Elapsed Epochs: $elapsed"
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
