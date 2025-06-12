package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Stochastic Gradient Descent optimizer with adjustable learning rate.
 *
 * @param learningRate The learning rate for the optimizer.
 */
class StochasticGradientDescent(
    val learningRate: Double
): Optimizer {

    init {
        require(learningRate > 0) { "Learning rate must be positive." }
    }

    override fun reset(layer: Layer) {
        TODO("Not yet implemented")
    }

    override fun update(layer: Layer, weightGradient: Tensor, biasGradient: Tensor) {
        TODO("Not yet implemented")
    }
}
