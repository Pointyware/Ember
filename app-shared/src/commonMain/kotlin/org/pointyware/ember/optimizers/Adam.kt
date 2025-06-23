package org.pointyware.ember.optimizers

import org.pointyware.ember.layers.Layer
import org.pointyware.ember.tensors.Tensor

/**
 * Adam optimizer.
 * Based on research paper: https://arxiv.org/pdf/1412.6980
 */
data class Adam(
    val alpha: Double = 0.001,
    val beta1: Double = 0.9,
    val beta2: Double = 0.999,
    val epsilon: Double = 1e-8,
): Optimizer {

    override fun sample(layer: Layer, activation: Tensor, derivative: Tensor) {
        TODO("Not yet implemented")
    }

    override fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
        TODO("Not yet implemented")
    }
}
