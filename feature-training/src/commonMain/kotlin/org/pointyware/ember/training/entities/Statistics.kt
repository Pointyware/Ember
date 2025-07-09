package org.pointyware.ember.training.entities

/**
 * A statistics object receives updates throughout training in order to collect
 * information to assess training performance.
 */
interface Statistics {
    fun onEpochStart(epoch: Int)
    fun onBatchStart(batch: List<Exercise>)
    fun onSampleStart(sample: Exercise)
    fun onCost(cost: Double)
    fun onGradient()
    fun onSampleEnd(sample: Exercise)
    fun onBatchEnd(batch: List<Exercise>)
    fun onEpochEnd(epoch: Int)
}
