package org.pointyware.ember.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.ember.viewmodels.LayerUiState


/**
 * Displays a single linear layer of a neural network. Neurons are displayed
 * from left to right, with the input on the top and the output on the bottom.
 */
@Composable
fun LayerView(
    state: LayerUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        ActivationFunctionIndicatorView(state.activationFunction)
        Column {
            for (index in state.weights.indices) {
                NeuronView(
                    weights = state.weights[index],
                    bias = state.biases[index],
                    colorMap = state.colorMap
                )
            }
        }
    }
}
