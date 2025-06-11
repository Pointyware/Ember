package org.pointyware.artes.scratch.loss

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A loss function computes the error between the predicted output and the target output.
 */
interface LossFunction {
    fun compute(predicted: Tensor, target: Tensor): Tensor
}
