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
        val maxParameter = input.trainer.network.layers.maxOf {
            max(it.biases.max { b -> abs(b) }, it.weights.max { w -> abs(w) })
        }

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
                            activationFunction = layer.activation,
                            colorMap = CenteredColorMap(
                                magnitudeClip = maxParameter
                            )
                        )
                    }
                )
            },
            statistics = StatisticsUiStateMapper.map(input.trainer.statistics)
        )
    }
}
