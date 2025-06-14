package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 *
 */
interface Optimizer {
    /**
     * Updates the parameters of the model based on the gradients computed during the forward pass.
     */
    fun update(layer: Layer, weightGradient: Tensor, biasGradient: Tensor)
}
