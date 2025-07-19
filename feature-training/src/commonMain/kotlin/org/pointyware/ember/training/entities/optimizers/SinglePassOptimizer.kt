package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise
import org.pointyware.ember.training.entities.Statistics

/**
 * A [Trainer][org.pointyware.ember.training.entities.Trainer] will track the activations
 * and derivatives of the model during the forward pass
 * and provide them to the [SinglePassOptimizer] to update the model parameters.
 */
interface Optimizer {
    /**
     * Creates training batches out of the given [cases].
     */
    fun batch(cases: List<Exercise>): List<List<Exercise>>
}

interface StatisticalOptimizer: Statistics, Optimizer

/**
 * An optimizer that only performs a single pass over training data before updating
 * model parameters.
 */
interface SinglePassOptimizer: Optimizer {

    /**
     * Updates the parameters of the model based on the outputs computed during the forward pass.
     *
     * @param epoch The current epoch.
     * @param layer The layer whose parameters are being updated.
     * @param weightGradients The gradients of the weights computed during the backward pass.
     * @param biasGradients The gradients of the biases computed during the backward pass.
     */
    fun update(epoch: Int, layer: Layer, weightGradients: Tensor, biasGradients: Tensor)
}

/**
 * An optimizer that performs multiple passes over training data, updating the model parameters
 * multiple times per epoch.
 */
interface MultiPassOptimizer: Optimizer {
    /**
     * Updates the parameters of the model based on the outputs computed during the forward pass.
     *
     * @param step The current step within an epoch.
     * @param epoch The current epoch.
     * @param layer The layer whose parameters are being updated.
     * @param weightGradients The gradients of the weights computed during the backward pass.
     * @param biasGradients The gradients of the biases computed during the backward pass.
     */
    fun update(step: Int, epoch: Int, layer: Layer, weightGradients: Tensor, biasGradients: Tensor)

    /**
     * Returns true if the optimizer should perform another pass over the training data.
     */
    fun passAgain(): Boolean
}
