package org.pointyware.ember.entities.layers

import org.pointyware.ember.entities.activations.ActivationFunction
import org.pointyware.ember.entities.tensors.Tensor

/**
 * Performs a linear transformation on the input with weights, biases, and activation function.
 *
 * Given a vector 'x' as input, m x n matrix of weights W, m x 1 matrix of biases B,
 * the output 'y' is computed as: `y = W * x + B`
 */
class LinearLayer(
    val weights: Tensor,
    val biases: Tensor,
    val activationFunction: ActivationFunction
): PreactivationLayer {

    override val parameterCount: Int
        get() = weights.totalSize + biases.totalSize + activationFunction.parameterCount

    override fun preactivation(
        input: Tensor,
        output: Tensor
    ) {
        output += weights.matrixMultiply(input)
        output += biases
    }

    override fun predict(
        input: Tensor,
        output: Tensor
    ) {
        val activation = Tensor(output.dimensions)
        preactivation(input, activation)
        output += activationFunction.calculate(activation)
    }

    /**
     * Multiplies the input by the weights and adds the biases. The weighted sum is
     * returned without applying the activation function to allow for optional
     * accumulation of gradients or further processing.
     */
    override fun forward(input: Tensor): Tensor { // TODO: replace with predict function that doesn't calculate intermediates
        val output = weights.matrixMultiply(input)
        output += biases
        return output
    }

    override fun forward(
        input: Tensor,
        activation: Tensor,
        derivative: Tensor
    ) {
        val preactivation = weights.matrixMultiply(input)
        preactivation += biases
        activationFunction.calculate(preactivation, activation, derivative)
    }

    override fun backward(
        error: Tensor,
        priorActivation: Tensor,
        priorActivationDerivative: Tensor,
        weightGradient: Tensor,
        biasGradient: Tensor,
        priorError: Tensor,
    ) {
        weightGradient += error.matrixMultiply(priorActivation.transpose())
        biasGradient += error

        // Propagate the error to the previous layer
        // ∂^l-1 = (f^l-1)' \dot (W^l)^T \dot; ∂^l
        val backpropError = if (priorActivationDerivative.dimensions.size == 1 ||
            priorActivationDerivative.dimensions[1] == 1) {
            // Diagonal case: element-wise multiplication
            priorActivationDerivative * weights.transpose().matrixMultiply(error)
        } else {
            // Full Jacobian case: matrix multiplication
            priorActivationDerivative.matrixMultiply(weights.transpose().matrixMultiply(error))
        }

        priorError.assign(backpropError)
    }

    companion object {
        /**
         * Creates a LinearLayer with the specified input and output dimensions.
         */
        fun create(inputSize: Int, outputSize: Int, activation: ActivationFunction): LinearLayer {
            val weights = Tensor.random(mean = 0.0f, stdDev = 0.1f, dimensions = intArrayOf(outputSize, inputSize))
            val biases = Tensor.zeros(outputSize, 1)
            return LinearLayer(weights, biases, activation)
        }
    }
}
