package org.pointyware.ember.training.entities

/**
 * A statistics object receives updates throughout training in order to collect
 * information to assess training performance.
 */
interface Statistics {
    fun onEpochStart()
    fun onBatchStart()
    fun onSampleStart()
    fun onCost(cost: Double)
    fun onGradient()
    fun onSampleEnd()
    fun onBatchEnd()
    fun onEpochEnd()
}
