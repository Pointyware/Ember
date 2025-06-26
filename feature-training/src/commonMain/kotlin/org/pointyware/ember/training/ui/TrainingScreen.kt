package org.pointyware.ember.training.ui

import androidx.compose.runtime.Composable
import org.pointyware.ember.training.viewmodels.TrainingViewModel
import org.pointyware.ember.ui.NeuralNetworkView
import org.pointyware.ember.ui.NeuralNetworkViewState

/**
 * The Training Screen
 */
@Composable
fun TrainingScreen(
    viewModel: TrainingViewModel,
    onBack: () -> Unit,
) {
    NeuralNetworkView(
        state = NeuralNetworkViewState(

        )
    )
}
