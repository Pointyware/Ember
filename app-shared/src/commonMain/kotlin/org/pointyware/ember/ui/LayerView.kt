package org.pointyware.ember.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


data class LayerViewState(
    val weights: List<List<Float>>,
    val biases: List<Float>,
    val activationFunction: ActivationFunctionIndicator
)

/**
 * Displays a single linear layer of a neural network. Neurons are displayed
 * from left to right, with the input on the top and the output on the bottom.
 */
@Composable
fun LayerView(
    state: LayerViewState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            ActivationFunctionIndicatorView(state.activationFunction)
        }
        items(state.biases.size) { index ->
            NeuronView(state.weights[index], state.biases[index])
        }
    }
}

/**
 * Displays a single neuron in a neural network layer.
 * The neuron is represented by its weights and bias.
 */
@Composable
fun NeuronView(
    weights: List<Float>,
    bias: Float,
    modifier: Modifier = Modifier
) {
    val displayString = remember(weights, bias) {
        buildString {
            append("Weights: [")
            append(weights.joinToString(", "))
            append("], Bias: ")
            append(bias)
        }
    }
    Text(
        text = displayString,
        modifier = modifier
    )
}
