package org.pointyware.artes.scratch.layers

import org.pointyware.artes.scratch.Marsaglia
import org.pointyware.artes.scratch.activations.ActivationFunction
import org.pointyware.artes.scratch.tensors.Tensor
import org.pointyware.artes.scratch.tensors.matrixOf

/**
 * Performs a linear transformation on the input with weights, biases, and activation function.
 *
 * Given a vector 'x' as input, m x n matrix of weights W, m x 1 matrix of biases B,
 * the output 'y' is computed as: `y = W * x + B`
 */
class LinearLayer(
    val weights: Tensor,
    val biases: Tensor,
    val activation: ActivationFunction
): Layer {

    /**
     * Multiplies the input by the weights and adds the biases. The weighted sum is
     * returned without applying the activation function to allow for optional
     * accumulation of gradients or further processing.
     */
    override fun forward(input: Tensor): Tensor {
        return weights.matrixMultiply(input) + biases
    }

    companion object {
        /**
         * Creates a LinearLayer with the specified input and output dimensions.
         */
        fun create(inputSize: Int, outputSize: Int, activation: ActivationFunction): LinearLayer {
            val weights = matrixOf(outputSize, inputSize).mapEachFlatIndexed { _, _ -> Marsaglia.getNormal() * 0.1 }
            val biases = matrixOf(outputSize, 1)
            return LinearLayer(weights, biases, activation)
        }
    }
}
