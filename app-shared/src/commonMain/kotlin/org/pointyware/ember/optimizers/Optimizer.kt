package org.pointyware.ember.optimizers

import org.pointyware.ember.layers.Layer
import org.pointyware.ember.tensors.Tensor

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
     * @param weightGradients The gradients of the weights computed during the backward pass.
     * @param biasGradients The gradients of the biases computed during the backward pass.
     */
    fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor)
}
