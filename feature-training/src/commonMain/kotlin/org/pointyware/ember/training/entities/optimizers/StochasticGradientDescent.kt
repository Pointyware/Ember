package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.training.entities.Exercise
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
    val batchSize: Int,
    learningRate: Float,
    entropy: Random = Random.Default,
): GradientDescent(learningRate, entropy) {

    init {
        require(batchSize > 0) { "Batch size must be greater than 0." }
    }

    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        return super.batch(cases)
    }
}
