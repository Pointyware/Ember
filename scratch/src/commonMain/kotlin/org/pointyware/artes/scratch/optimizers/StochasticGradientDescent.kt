package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Stochastic Gradient Descent (SGD) optimizer with adjustable learning rate.
 *
 * SGD operates on a subset of the training data (a single sample or a mini-batch) randomly
 * selected from the entire dataset.
 *
 * @param learningRate The learning rate for the optimizer.
 */
class StochasticGradientDescent(
    val learningRate: Double
): Optimizer {

    init {
        require(learningRate > 0) { "Learning rate must be positive." }
    }

    override fun update(layer: Layer, weightGradient: Tensor, biasGradient: Tensor) {
        when (layer) {
            is LinearLayer -> {
                layer.weights.mapEachIndexed { indices, input ->
                    input - learningRate * weightGradient[indices]
                }
                layer.biases.mapEachIndexed { indices, input ->
                    input - learningRate * biasGradient[indices]
                }
            }
        }
    }
}
