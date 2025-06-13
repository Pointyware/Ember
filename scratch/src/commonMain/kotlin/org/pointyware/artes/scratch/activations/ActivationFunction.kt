package org.pointyware.artes.scratch.activations

/**
 * An activation function takes the input to a neuron and produces an output.
 */
interface ActivationFunction {
    /**
     * Calculates the output of the activation function for the given input tensor.
     */
    fun calculate(input: Double): Double

    /**
     * Calculates the derivative of the activation function for the given input tensor.
     */
    fun derivative(input: Double): Double
}
