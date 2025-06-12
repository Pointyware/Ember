package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 *
 */
interface Optimizer {
    /**
     * Resets the state of the optimizer, including any accumulated gradients or state.
     */
    fun reset(layer: Layer)

    /**
     * Updates the parameters of the model based on the gradients computed during the forward pass.
     */
    fun update(layer: Layer, weightGradient: Tensor, biasGradient: Tensor)
}
