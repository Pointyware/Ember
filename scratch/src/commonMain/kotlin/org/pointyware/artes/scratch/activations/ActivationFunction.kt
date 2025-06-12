package org.pointyware.artes.scratch.activations

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * An activation function takes the input to a neuron and produces an output.
 */
interface ActivationFunction {
    fun calculate(input: Tensor): Tensor
}
