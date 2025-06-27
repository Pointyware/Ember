package org.pointyware.ember.training.viewmodels

import org.pointyware.ember.ui.NeuralNetworkViewState

/**
 * Represents the state for a network training UI.
 */
data class TrainingUiState(
    val isTraining: Boolean,
    val epochsRemaining: Int,
    val networkState: NeuralNetworkViewState
) {
    companion object {
        val Default = TrainingUiState(
            isTraining = false,
            epochsRemaining = 0,
            networkState = NeuralNetworkViewState()
        )
    }
}
