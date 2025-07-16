package org.pointyware.ember.training.viewmodels

import org.pointyware.artes.common.Mapper
import org.pointyware.ember.training.interactors.TrainingState
import org.pointyware.ember.viewmodels.CenteredColorMap
import org.pointyware.ember.viewmodels.LayerUiState
import org.pointyware.ember.viewmodels.NeuralNetworkUiState
import kotlin.math.abs
import kotlin.math.max

object TrainingUiStateMapper: Mapper<TrainingState, TrainingUiState> {

    override fun map(input: TrainingState): TrainingUiState {
        val maxParameter = input.networks.maxOf { network ->
            network.trainer.network.layers.maxOf {
                max(it.biases.max { b -> abs(b) }, it.weights.max { w -> abs(w) })
            }
        }

        return TrainingUiState(
            epochsRemaining = input.epochsRemaining,
            epochsElapsed = input.epochsElapsed,
            isTraining = input.isTraining,
            networks = input.networks.map { network ->
                NetworkTrainingUiState(
                    epochsTrained = network.elapsedEpochs,
                    networkState = network.trainer.network.let { network ->
                        NeuralNetworkUiState(
                            layers = network.layers.map { layer ->
                                LayerUiState(
                                    weights = layer.weights.toListMatrix(),
                                    biases = layer.biases.toListVector(),
                                    activationFunction = layer.activationFunction,
                                    colorMap = CenteredColorMap(
                                        magnitudeClip = maxParameter
                                    )
                                )
                            }
                        )
                    },
                    statistics = StatisticsUiStateMapper.map(network.snapshot)
                )
            }
        )
    }
}
