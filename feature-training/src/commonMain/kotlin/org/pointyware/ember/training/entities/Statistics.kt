package org.pointyware.ember.training.entities

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * A statistics object receives updates throughout training in order to collect
 * information to assess training performance.
 */
interface Statistics {
    /**
     * Lists the measurements taken by this object.
     */
    val measurements: List<Measurement>
    fun measurementMaximum(key: Measurement): Float
    val measurementsMax: Float
    val epochCount: Int
    fun data(key: Measurement): List<Pair<Float, Float>>

    /**
     * Collects all measures into a single immutable object
     */
    fun createSnapshot(): Snapshot
}

/**
 *
 */
@OptIn(ExperimentalTime::class)
data class Snapshot(
    val epoch: Int,
    val measurements: Map<Measurement, List<Pair<Float, Float>>>,
    val timestamp: Instant = Clock.System.now(),
) {
    companion object {
        val empty = Snapshot(
            epoch = 0,
            measurements = emptyMap()
        )
    }
}

/**
 *
 */
interface EpochStatistics: Statistics {
    fun onEpochStart(epoch: Int)
    fun onEpochEnd(epoch: Int)
}

/**
 *
 */
interface BatchStatistics: Statistics {
    fun onBatchStart(batch: List<Exercise>)
    fun onBatchEnd(batch: List<Exercise>)
}

/**
 *
 */
interface SampleStatistics: Statistics {
    fun onSampleStart(sample: Exercise)
    fun onCost(cost: Double)
    fun onGradient()
    fun onSampleEnd(sample: Exercise)
}

/**
 *
 */
interface LayerStatistics: Statistics {
    fun onPreActivation()
    fun onActivation()
    fun onGradient()
}
