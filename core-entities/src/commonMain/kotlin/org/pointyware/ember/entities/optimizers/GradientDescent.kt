package org.pointyware.ember.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.random.Random

/**
 * An optimizer that works by caching calculations during the forward pass
 * and calculating gradients during the backward pass.
 */
open class GradientDescent(
    val learningRate: Double,
    val entropy: Random = Random.Default,
): Optimizer {
    init {
        require(learningRate > 0) { "Learning rate must be positive." }
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
