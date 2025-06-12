package org.pointyware.artes.scratch.layers

import org.pointyware.artes.scratch.activations.ActivationFunction
import org.pointyware.artes.scratch.tensors.LearningTensor
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Performs a linear transformation on the input with weights, biases, and activation function.
 */
class LinearLayer(
    val weights: LearningTensor,
    val biases: LearningTensor,

    val activation: ActivationFunction
): Layer {

    override fun forward(input: Tensor): Tensor {
        val weightedInput = input.matrixMultiply(weights) + biases
        return activation.calculate(weightedInput)
    }

    override fun backward(gradient: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    companion object {
        /**
         * Creates a LinearLayer with the specified input and output dimensions.
         */
        fun create(inputSize: Int, outputSize: Int, activation: ActivationFunction): LinearLayer {
            val weights = LearningTensor.random(outputSize, inputSize)
            val biases = LearningTensor.zeros(outputSize)
            return LinearLayer(weights, biases, activation)
        }
    }
}
