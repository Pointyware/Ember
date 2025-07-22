package org.pointyware.ember.entities.networks

import org.pointyware.ember.entities.activations.ActivationFunction
import org.pointyware.ember.entities.layers.DenseLayer
import org.pointyware.ember.entities.tensors.Tensor

/**
 * A sequential network is a series of layers that are applied sequentially,
 * without branches or skips.
 */
open class SequentialNetwork(
    val layers: List<DenseLayer>,
) : Network {
    override val parameterCount: Int
        get() = layers.sumOf { it.parameterCount }

    override fun predict(input: Tensor): Tensor {
        return layers.fold(input) { acc, layer ->
            layer.activationFunction.calculate(layer.forward(acc))
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
            val activation = layer.activationFunction.calculate(weightedInputs)
            val derivativeActivation = layer.activationFunction.derivative(weightedInputs)
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
        // ∂_L = (f^L)' \dot (a_L - y);
        var layerError: Tensor = derivativeActivations.last() * error
        layers.indices.reversed().forEach back_pass@ { layerIndex ->
            val inputs = if (layerIndex == 0) { input } else { activations[layerIndex - 1] }

            // Skip the first layer as it has no previous layer to propagate error to
            val priorActivationDerivative = if (layerIndex == 0) {
                Tensor.ones(input.dimensions)
            } else {
                derivativeActivations[layerIndex - 1]
            }
            val priorError = Tensor(inputs.dimensions)
            layers[layerIndex].backward( // TODO: replace with calculation graph traversal and context map
                layerError,
                inputs, priorActivationDerivative,
                weightGradients[layerIndex], biasGradients[layerIndex],
                priorError
            )
            layerError = priorError
        }
    }

    companion object {
        fun create(input: Int, vararg layers: Pair<Int, ActivationFunction>): SequentialNetwork {
            require(layers.isNotEmpty()) { "At least one layer must be specified." }
            var inputSize = input
            val networkLayers = layers.map { (outputSize, activation) ->
                DenseLayer.create(inputSize, outputSize, activation).also {
                    inputSize = outputSize // Update input size for the next layer
                }
            }
            return SequentialNetwork(networkLayers)
        }
    }
}
