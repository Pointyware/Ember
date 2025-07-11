package org.pointyware.ember.training.entities

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
    fun data(it: Measurement): List<Pair<Float, Float>>

    fun onEpochStart(epoch: Int)
    fun onBatchStart(batch: List<Exercise>)
    fun onSampleStart(sample: Exercise)
    fun onCost(cost: Double)
    fun onGradient()
    fun onSampleEnd(sample: Exercise)
    fun onBatchEnd(batch: List<Exercise>)
    fun onEpochEnd(epoch: Int)
}

/**
 *
 */
data class Measurement(
    val name: String,
    val unit: Subject
) {
    enum class Subject {
        Error,
        Accuracy,
        Gradient,
    }
}
