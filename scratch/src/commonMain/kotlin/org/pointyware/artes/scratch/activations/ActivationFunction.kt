package org.pointyware.artes.scratch.activations

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * An activation function takes the input to a neuron and produces an output.
 */
interface ActivationFunction {
    /**
     * Calculates the output of the activation function for the given input tensor.
     */
    fun calculate(input: Tensor): Tensor

    /**
     * Calculates the derivative of the activation function for the given input tensor.
     */
    fun derivative(input: Tensor): Tensor
}

interface ScalarActivationFunction: ActivationFunction {
    fun scalarActivation(input: Double): Double
    fun scalarDerivative(input: Double): Double

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
}
