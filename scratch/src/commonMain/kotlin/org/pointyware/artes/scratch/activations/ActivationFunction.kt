package org.pointyware.artes.scratch.activations

import org.pointyware.artes.scratch.tensors.SimpleTensor
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
        return SimpleTensor(input.dimensions).apply {
            mapEach { value ->
                scalarActivation(value)
            }
        }
    }

    override fun derivative(input: Tensor): Tensor {
        return SimpleTensor(input.dimensions).apply {
            mapEach { value ->
                scalarDerivative(value)
            }
        }
    }
}
