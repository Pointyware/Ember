package org.pointyware.ember.training.viewmodels

/**
 * Represents the state for a network training UI.
 */
data class TrainingUiState(
    val isTraining: Boolean,
    val epochsRemaining: Int,
) {
    companion object {
        val Default = TrainingUiState(
            isTraining = false,
            epochsRemaining = 0
        )
    }
}
