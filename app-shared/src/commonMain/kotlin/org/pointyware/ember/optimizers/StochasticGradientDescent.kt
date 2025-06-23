package org.pointyware.ember.optimizers

import org.pointyware.ember.layers.Layer
import org.pointyware.ember.layers.LinearLayer
import org.pointyware.ember.tensors.Tensor
import kotlin.random.Random

/**
 * Stochastic Gradient Descent (SGD) optimizer with adjustable learning rate.
 *
 * SGD operates on a subset of the training data (a single sample or a mini-batch) randomly
 * selected from the entire dataset.
 *
 * @param learningRate The learning rate for the optimizer.
 */
class StochasticGradientDescent(
    val learningRate: Double,
    val samplingRate: Double = 0.1,
    val entropy: Random = Random.Default,
): Optimizer {

    init {
        require(samplingRate in 0.0..1.0) { "Sampling rate must be between 0.0 and 1.0." }
        require(learningRate > 0) { "Learning rate must be positive." }
    }

    override fun sample(layer: Layer, activation: Tensor, derivative: Tensor) {
        if (entropy.nextDouble() <= samplingRate) {
            TODO("Sample the layer with the given activation and derivative tensors.")
        }
    }

    override fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
        when (layer) {
            is LinearLayer -> {
                layer.weights.mapEachFlatIndexed { index, value ->
                    value - learningRate * weightGradients[index]
                }
                layer.biases.mapEachFlatIndexed { index, value ->
                    value - learningRate * biasGradients[index]
                }
            }

            else -> {
                throw IllegalArgumentException("Unsupported layer type: ${layer::class.simpleName}")
            }
        }
    }
}
