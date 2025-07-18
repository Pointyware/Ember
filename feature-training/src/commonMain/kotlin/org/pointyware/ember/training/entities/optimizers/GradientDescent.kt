package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise
import kotlin.random.Random

/**
 * An optimizer that works by caching calculations during the forward pass
 * and calculating gradients during the backward pass.
 *
 * All cases are grouped into a single batch.
 */
open class GradientDescent(
    val learningRate: Float,
    val entropy: Random = Random.Default,
): Optimizer {
    init {
        require(learningRate > 0) { "Learning rate must be positive." }
    }

    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        return listOf(cases)
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
