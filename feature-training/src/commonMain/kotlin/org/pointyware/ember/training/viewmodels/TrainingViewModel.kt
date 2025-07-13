package org.pointyware.ember.training.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.interactors.TrainingController

/**
 * This view model maintains the ui state for training a neural network.
 */
class TrainingViewModel(
    private val controller: TrainingController
): ViewModel() {

    /**
     * The current state of the training UI.
     */
    val state: StateFlow<TrainingUiState>
        get() = controller.state.map(TrainingUiStateMapper::map).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = TrainingUiState.Default
        )

    fun onStart() {
        controller.start()
    }

    fun onStop() {
        controller.stop()
    }

    fun onRun(epochs: Int) {
        if (epochs <= 0) {
            throw IllegalArgumentException("Number of epochs must be greater than 0")
        }
        controller.setEpochs(epochs)
        controller.start()
    }

    /**
     * Reset current training session.
     */
    fun onReset() {
        controller.reset()
    }

    /**
     * Load a neural network for training.
     */
    fun loadNetwork(networkId: String) {
        // TODO: actually load the network
    }
}

fun Tensor.toListMatrix(): List<List<Float>> {
    return (0 until this.dimensions[0]).map { row ->
        (0 until this.dimensions[1]).map { col ->
            this[row, col].toFloat()
        }
    }
}

fun Tensor.toListVector(): List<Float> {
    return (0 until this.dimensions[0]).map { index ->
        this[index].toFloat()
    }
}
