package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise
import org.pointyware.ember.training.entities.Statistics

/**
 * Adam optimizer.
 * Based on research paper: https://arxiv.org/pdf/1412.6980
 */
data class Adam(
    val alpha: Double = 0.001,
    val beta1: Double = 0.9,
    val beta2: Double = 0.999,
    val epsilon: Double = 1e-8,
    val statistics: Statistics
): Optimizer, Statistics by statistics {
    // TODO: allow multiple passes over samples
    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
        TODO("Not yet implemented")
    }
}
