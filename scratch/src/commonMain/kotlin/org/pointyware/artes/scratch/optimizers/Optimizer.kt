package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A [Trainer] will track the activations and derivatives of the model during the forward pass
 * and provide them to the [Optimizer] to update the model parameters.
 *
 *
 */
interface Optimizer {

    /**
     *
     */
    fun sample(layer: Layer, activation: Tensor, derivative: Tensor)

    /**
     * Updates the parameters of the model based on the outputs computed during the forward pass.
     *
     * @return The updated tensor after applying the optimizer.
     */
    fun update(layer: Layer, activation: Tensor, derivative: Tensor): Tensor
}
