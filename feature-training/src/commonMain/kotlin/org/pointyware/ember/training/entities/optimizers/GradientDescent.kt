package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.layers.DenseLayer
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
    val learningRate: LearningRateSchedule,
    val entropy: Random = Random.Default,
    // TODO: pass in value keys for retrieval from computation context
): SinglePassOptimizer {

    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        return listOf(cases)
    }

    override fun update(epoch: Int, layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
//        val epoch = TODO("Pass In ComputationContext")
        val currentLearningRate = learningRate.learningRate(epoch)
        when (layer) {
            is DenseLayer -> {
                layer.weights.mapEachFlatIndexed { index, value ->
                    value - currentLearningRate * weightGradients[index]
                }
                layer.biases.mapEachFlatIndexed { index, value ->
                    value - currentLearningRate * biasGradients[index]
                }
            }

            else -> {
                throw IllegalArgumentException("Unsupported layer type: ${layer::class.simpleName}")
            }
        }
    }
}
