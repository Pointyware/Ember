package org.pointyware.ember.training.entities.optimizers.optimizers

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
    val samplingRate: Float = 0.1f,
    learningRate: Float,
    entropy: Random = Random.Default,
): GradientDescent(learningRate, entropy) {

    init {
        require(samplingRate in 0.0f..1.0f) { "Sampling rate must be between 0.0 and 1.0." }
    }

    override fun doSample(case: Exercise): Boolean {
        return entropy.nextDouble() < samplingRate
    }
}
