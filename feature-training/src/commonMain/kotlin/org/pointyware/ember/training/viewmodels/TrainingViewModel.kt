package org.pointyware.ember.training.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.pointyware.ember.training.data.TrainingController

/**
 * This view model maintains the ui state for training a neural network.
 */
class TrainingViewModel(
    private val controller: TrainingController
): ViewModel() {

    val state: StateFlow<TrainingUiState>
        get() = controller.state.map {
            TrainingUiState(
                epochsRemaining = it.epochsRemaining,
                isTraining = it.isTraining,
                // TODO: add network parameters
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = TrainingUiState.Default
        )

    /**
     * Reset current training session.
     */
    fun reset() {
        controller.reset()
    }

    fun loadNetwork(networkId: String) {
        // TODO: actually load the network
    }
}
