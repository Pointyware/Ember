package org.pointyware.artes.scratch.networks

import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A sequential network is a series of layers that are applied sequentially,
 * without branches or skips.
 */
class SequentialNetwork(
    val layers: List<LinearLayer>,
) : Network {
    override fun predict(input: Tensor): Tensor {
        return layers.fold(input) { acc, layer ->
            layer.forward(acc)
        }
    }

    override fun forward(
        input: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>
    ) {
        var networkLayerState = input
        layers.forEachIndexed { layerIndex, layer ->
            val weightedInputs = layer.forward(networkLayerState)
            val activation = layer.activation.calculate(weightedInputs)
            val derivativeActivation = layer.activation.derivative(weightedInputs)
            activations[layerIndex] += activation
            derivativeActivations[layerIndex] += derivativeActivation
            networkLayerState = activation
        }
    }

    override fun backward(
        error: Tensor,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    ) {
        var layerError: Tensor = derivativeActivations.last() * error
        layers.indices.reversed().forEach back_pass@ { layerIndex ->
            // Skip the first layer as it has no previous layer to propagate error to
            if (layerIndex == 0) return@back_pass

            weightGradients[layerIndex] += layerError.matrixMultiply(derivativeActivations[layerIndex - 1].transpose())
            biasGradients[layerIndex] += layerError

            // Skip the first layer as it has no previous layer to propagate error to
            if (layerIndex == 1) return@back_pass
            // Propagate the error to the previous layer
            val weightsTranspose = layers[layerIndex].weights.transpose()
            layerError = derivativeActivations[layerIndex - 1] *
                    weightsTranspose.matrixMultiply(layerError)
        }
    }

    override fun updateWeights(weightGradients: List<Tensor>, biasGradients: List<Tensor>) {
        layers.forEachIndexed { layerIndex, layer ->
            val weightGradient = weightGradients[layerIndex]
            val biasGradient = biasGradients[layerIndex]
            weightGradient.mapEachFlatIndexed { index, value ->
                value - weightGradient[index]
            }
            biasGradient.mapEachFlatIndexed { index, value ->
                value - biasGradient[index]
            }
        }
    }
}
