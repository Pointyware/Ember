package org.pointyware.ember.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.entities.activations.Logistic
import org.pointyware.ember.ui.theme.EmberTheme
import org.pointyware.ember.viewmodels.CenteredColorMap
import org.pointyware.ember.viewmodels.LayerUiState
import org.pointyware.ember.viewmodels.NeuralNetworkUiState

@Preview
@Composable
private fun NeuralNetworkViewPreview() {
    val consistentScale = CenteredColorMap(
        magnitudeClip = 0.7f,
    )
    EmberTheme {
        NeuralNetworkView(
            state = NeuralNetworkUiState(
                layers = listOf(
                    LayerUiState(
                        activationFunction = ReLU,
                        weights = listOf(
                            listOf(0.1f, 0.2f, 0.5f),
                            listOf(-0.3f, 0.4f, -0.6f),
                            listOf(0.5f, 0.6f, 0.7f)
                        ),
                        biases = listOf(0.1f, -0.2f, 0.3f),
                        colorMap = consistentScale,
                    ),
                    LayerUiState(
                        activationFunction = Logistic,
                        weights = listOf(
                            listOf(-0.7f, 0.8f),
                            listOf(-0.1f, -0.2f)
                        ),
                        biases = listOf(0.1f, 0.2f),
                        colorMap = consistentScale,
                    ),
                    LayerUiState(
                        activationFunction = Logistic,
                        weights = listOf(
                            listOf(-0.7f, 0.8f),
                            listOf(-0.1f, -0.2f)
                        ),
                        biases = listOf(0.1f, 0.2f),
                        colorMap = consistentScale,
                    )
                ),
            )
        )
    }
}
