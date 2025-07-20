package org.pointyware.ember.entities.activations

import org.pointyware.ember.entities.tensors.Tensor

/**
 * An activation function takes the input to a neuron and produces an output.
 */
interface ActivationFunction {
    val parameterCount: Int
    /**
     * Calculates the output of the activation function for the given input tensor.
     */
    fun calculate(input: Tensor): Tensor

    /**
     * Calculates the derivative of the activation function for the given input tensor.
     */
    fun derivative(input: Tensor): Tensor

    /**
     * Calculates the output of the activation function and its derivative for the
     * given input tensor and stores the results in the provided tensors.
     */
    fun calculate(input: Tensor, activation: Tensor, derivativeActivation: Tensor)
}

interface ScalarActivationFunction: ActivationFunction {
    fun scalarActivation(input: Float): Float
    fun scalarDerivative(input: Float): Float

    override fun calculate(input: Tensor): Tensor {
        return Tensor(input.dimensions).mapEachFlatIndexed { index, _ ->
            scalarActivation(input[index])
        }
    }

    override fun derivative(input: Tensor): Tensor {
        return Tensor(input.dimensions).mapEachFlatIndexed { index, _ ->
            scalarDerivative(input[index])
        }
    }

    override fun calculate(input: Tensor, activation: Tensor, derivativeActivation: Tensor) {
        input.flatIndices.forEach { index ->
            val value = input[index]
            activation[index] = scalarActivation(value)
            derivativeActivation[index] = scalarDerivative(value)
        }
    }
}
