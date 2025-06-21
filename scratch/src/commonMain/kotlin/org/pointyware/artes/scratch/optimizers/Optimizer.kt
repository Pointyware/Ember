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
     * @param layer The layer whose parameters are being updated.
     * @param activation The activation tensor from the forward pass.
     * @param derivative The derivative tensor from the forward pass.
     * @param error The error tensor computed from the loss function.
     */
    fun update(layer: Layer, priorActivationDerivative: Tensor, error: Tensor)
}
