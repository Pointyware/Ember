package org.pointyware.ember.training.entities.optimizers

import kotlin.math.pow

/**
 * Allows an [Optimizer] to delegate the learning rate to increase composability of
 * different optimizers.
 */
interface LearningRateSchedule {
    fun learningRate(epoch: Int): Float
}

interface StepLearningRateSchedule: LearningRateSchedule {
    
}

data class ConstantLearningRate(val learningRate: Float): LearningRateSchedule {
    override fun learningRate(epoch: Int): Float = learningRate
}

/**
 * This learning rate schedule begins at the [initialLearningRate] and decays exponentially
 * until it reaches [initialLearningRate] / [decayMax] over [decayPeriod] epochs.
 */
data class WarmRestartExponentialLearningRate(
    val initialLearningRate: Float,
    val decayMax: Float = 100f,
    val decayPeriod: Int = 10000,
): LearningRateSchedule {
    override fun learningRate(epoch: Int): Float {
        val cyclePhase = epoch % decayPeriod
        return initialLearningRate / decayMax.pow(cyclePhase / decayPeriod.toFloat())
    }
}
