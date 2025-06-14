package org.pointyware.artes.scratch.layers

import org.pointyware.artes.scratch.Marsaglia
import org.pointyware.artes.scratch.activations.ActivationFunction
import org.pointyware.artes.scratch.tensors.SimpleTensor
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Performs a linear transformation on the input with weights, biases, and activation function.
 */
class LinearLayer(
    val weights: SimpleTensor,
    val biases: SimpleTensor,
    val activation: ActivationFunction
): Layer {

    override fun forward(input: Tensor): Tensor {
        return input.matrixMultiply(weights) + biases
    }

    override fun backward(gradient: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    companion object {
        /**
         * Creates a LinearLayer with the specified input and output dimensions.
         */
        fun create(inputSize: Int, outputSize: Int, activation: ActivationFunction): LinearLayer {
            val weights = SimpleTensor.from(outputSize, inputSize).apply { mapEach { Marsaglia.getNormal() }}
            val biases = SimpleTensor.from(outputSize)
            return LinearLayer(weights, biases, activation)
        }
    }
}
