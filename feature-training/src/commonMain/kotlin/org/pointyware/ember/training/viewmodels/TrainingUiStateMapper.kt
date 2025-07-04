package org.pointyware.ember.training.viewmodels

import org.pointyware.artes.common.Mapper
import org.pointyware.ember.training.data.TrainingState
import org.pointyware.ember.viewmodels.LayerUiState
import org.pointyware.ember.viewmodels.NeuralNetworkUiState

object TrainingUiStateMapper: Mapper<TrainingState, TrainingUiState> {

    override fun map(input: TrainingState): TrainingUiState {
        return TrainingUiState(
            epochsRemaining = input.epochsRemaining,
            epochsTrained = input.elapsedEpochs,
            isTraining = input.isTraining,
            networkState = input.trainer.network.let { network ->
                NeuralNetworkUiState(
                    layers = network.layers.map { layer ->
                        LayerUiState(
                            weights = layer.weights.toListMatrix(),
                            biases = layer.biases.toListVector(),
                            activationFunction = layer.activation
                        )
                    }
                )
            }
        )
    }
}
