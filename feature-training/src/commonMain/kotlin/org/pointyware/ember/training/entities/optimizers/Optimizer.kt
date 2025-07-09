package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise

/**
 * A [Trainer][org.pointyware.ember.entities.training.Trainer] will track the activations
 * and derivatives of the model during the forward pass
 * and provide them to the [Optimizer] to update the model parameters.
 *
 */
interface Optimizer {

    /**
     * Selects a subset of [Exercise] instances for training based
     * on the optimizer's sampling strategy. The default
     * implementation filters the cases using [doSample].
     */
    fun sample(cases: List<Exercise>): List<Exercise> {
        return cases.filter { doSample(it) }
    }

    /**
     * Determines whether a given [Exercise] should be selected for training.
     * If [sample] is overridden, this method can be ignored, unless it is
     * used in your implementation.
     */
    fun doSample(case: Exercise): Boolean = true

    /**
     * Updates the parameters of the model based on the outputs computed during the forward pass.
     *
     * @param layer The layer whose parameters are being updated.
     * @param weightGradients The gradients of the weights computed during the backward pass.
     * @param biasGradients The gradients of the biases computed during the backward pass.
     */
    fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor)
}
