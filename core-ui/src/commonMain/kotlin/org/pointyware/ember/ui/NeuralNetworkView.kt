package org.pointyware.ember.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pointyware.ember.ui.theme.EmberTheme
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(EmberTheme.geometry.paddingSmall)
    ) {
        state.layers.forEach { layerState ->
            LayerView(
                state = layerState,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun LazyNeuralNetworkView(
    state: NeuralNetworkUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(EmberTheme.geometry.paddingSmall)
    ) {
        items(state.layers) { layerState ->
            LayerView(
                state = layerState,
                modifier = Modifier
            )
        }
    }
}
