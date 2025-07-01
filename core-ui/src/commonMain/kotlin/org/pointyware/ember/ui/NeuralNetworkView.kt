package org.pointyware.ember.ui

import androidx.compose.foundation.lazy.LazyColumn
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
    LazyColumn(
        modifier = modifier
    ) {
        items(state.layers) { layerState ->
            LayerView(
                state = layerState,
                modifier = Modifier
            )
        }
    }
}
