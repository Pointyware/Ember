package org.pointyware.ember.training.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.training.viewmodels.TrainingUiState
import org.pointyware.ember.viewmodels.LayerUiState
import org.pointyware.ember.viewmodels.NeuralNetworkUiState

@Preview
@Composable
private fun TrainingViewPreview() {
    TrainingView(
        state = TrainingUiState(
            isTraining = false,
            epochsTrained = 10,
            epochsRemaining = 20,
            networkState = NeuralNetworkUiState(
                layers = listOf(
                    LayerUiState(
                        activationFunction = ReLU,
                        weights = listOf(
                            listOf(0.1f, 0.2f),
                            listOf(-0.3f, 0.4f),
                            listOf(0.5f, 0.6f)
                        ),
                        biases = listOf(0.1f, -0.2f, 0.3f),
                    ),
                    LayerUiState(
                        activationFunction = ReLU,
                        weights = listOf(
                            listOf(-0.7f, 0.8f),
                            listOf(-0.1f, -0.2f)
                        ),
                        biases = listOf(0.1f, 0.2f),
                    )
                )
            )
        ),
        onStart = {},
        onStop = {},
        onReset = {},
        onRun = {},
    )
}
