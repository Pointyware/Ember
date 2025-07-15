package org.pointyware.ember.training.viewmodels


/**
 * Represents the state for all networks currently being trained.
 */
data class TrainingUiState(
    val isTraining: Boolean,
    val epochsRemaining: Int,
    val networks: List<NetworkTrainingUiState>
) {
    companion object {
        val Default = TrainingUiState(
            isTraining = false,
            epochsRemaining = 0,
            networks = emptyList()
        )
    }
}
