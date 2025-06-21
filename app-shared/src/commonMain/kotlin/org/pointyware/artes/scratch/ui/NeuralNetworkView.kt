package org.pointyware.artes.scratch.ui

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class NeuralNetworkViewState(
    val layers: List<LayerViewState> = emptyList(),
    val parameters: Map<String, Any> = emptyMap()
)

/**
 * Displays a sequential linear neural network. The view is oriented with
 * the input layer on the top and the output layer on the bottom.
 */
@Composable
fun NeuralNetworkView(
    state: NeuralNetworkViewState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(state.layers) { layerState ->
            LayerView(
                layerState,
                modifier = modifier
            )
        }
    }
}
