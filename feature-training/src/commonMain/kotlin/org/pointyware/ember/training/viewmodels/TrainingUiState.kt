package org.pointyware.ember.training.viewmodels

import org.pointyware.ember.ui.NeuralNetworkViewState

/**
 * Represents the state for a network training UI.
 *
 * @param epochsTrained The current epoch number in the training process. 0 indicates no
 * elapsed epochs.
 */
data class TrainingUiState(
    val isTraining: Boolean,
    val epochsTrained: Int,
    val epochsRemaining: Int,
    val networkState: NeuralNetworkViewState
) {
    companion object {
        val Default = TrainingUiState(
            isTraining = false,
            epochsTrained = 0,
            epochsRemaining = 0,
            networkState = NeuralNetworkViewState()
        )
    }
}
