package org.pointyware.artes.scratch.layers

import org.pointyware.artes.scratch.Marsaglia
import org.pointyware.artes.scratch.activations.ActivationFunction
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Performs a linear transformation on the input with weights, biases, and activation function.
 */
class LinearLayer(
    val weights: Tensor,
    val biases: Tensor,
    val activation: ActivationFunction
): Layer {

    override fun forward(input: Tensor): Tensor {
        return weights * input + biases
    }

    companion object {
        /**
         * Creates a LinearLayer with the specified input and output dimensions.
         */
        fun create(inputSize: Int, outputSize: Int, activation: ActivationFunction): LinearLayer {
            val weights = Tensor.shape(outputSize, inputSize).apply { mapEach { Marsaglia.getNormal() }}
            val biases = Tensor.shape(outputSize)
            return LinearLayer(weights, biases, activation)
        }
    }
}
