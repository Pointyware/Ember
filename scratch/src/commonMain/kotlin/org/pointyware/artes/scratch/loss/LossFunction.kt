package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A loss function computes the error between the predicted output and the target output.
 */
interface LossFunction {
    fun compute(expected: Tensor, actual: Tensor): Double

    /**
     * Calculates the derivative of the loss function with respect to the actual output.
     *
     * Conceptually the same as the gradient of the loss function.
     */
    fun derivative(expected: Tensor, actual: Tensor): Tensor
}
