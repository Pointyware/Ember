package org.pointyware.ember.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.ember.viewmodels.NeuralNetworkUiState

/**
 * Displays a sequential linear neural network. The view is oriented with
 * the input layer on the top and the output layer on the bottom.
 */
@Composable
fun NeuralNetworkView(
    state: NeuralNetworkUiState,
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
