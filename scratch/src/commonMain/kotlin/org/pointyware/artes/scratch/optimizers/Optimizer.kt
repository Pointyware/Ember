package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 *
 */
interface Optimizer {
    /**
     * Updates the parameters of the model based on the outputs computed during the forward pass.
     *
     * @return The updated tensor after applying the optimizer.
     */
    fun update(layer: Layer, activation: Tensor, derivative: Tensor): Tensor
}
