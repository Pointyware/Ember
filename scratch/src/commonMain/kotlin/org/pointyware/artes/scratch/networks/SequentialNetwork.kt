package org.pointyware.artes.scratch.networks

import org.pointyware.artes.scratch.activations.ActivationFunction
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
            layer.activation.calculate(layer.forward(acc))
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
        input: Tensor,
        error: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    ) {
        // ∂^L = (f^L)' \dot (a^L - y);
        var layerError: Tensor = derivativeActivations.last() * error
        layers.indices.reversed().forEach back_pass@ { layerIndex ->
            val inputs = if (layerIndex == 0) { input } else { activations[layerIndex - 1] }
            weightGradients[layerIndex] += layerError.matrixMultiply(inputs.transpose())
            biasGradients[layerIndex] += layerError

            // Skip the first layer as it has no previous layer to propagate error to
            if (layerIndex == 0) return@back_pass
            // Propagate the error to the previous layer
            // ∂^l-1 = (f^l-1)' \dot (W^l)^T \dot; ∂^l
            layerError = derivativeActivations[layerIndex - 1] *
                    layers[layerIndex].weights.transpose().matrixMultiply(layerError)
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

    companion object {
        fun create(input: Int, vararg layers: Pair<Int, ActivationFunction>): SequentialNetwork {
            require(layers.isNotEmpty()) { "At least one layer must be specified." }
            var inputSize = input
            val networkLayers = layers.map { (outputSize, activation) ->
                LinearLayer.create(inputSize, outputSize, activation).also {
                    inputSize = outputSize // Update input size for the next layer
                }
            }
            return SequentialNetwork(networkLayers)
        }
    }
}
