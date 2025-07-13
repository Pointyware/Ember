package org.pointyware.ember.training.entities

/**
 * A statistics object receives updates throughout training in order to collect
 * information to assess training performance.
 */
interface Statistics {

    /**
     * The update period controls how frequently measurements are taken.
     */
    val updatePeriod: Int

    /**
     * Lists the measurements taken by this object.
     */
    val measurements: List<Measurement>
    fun measurementMaximum(key: Measurement): Float
    val measurementsMax: Float
    val epochCount: Int
    fun data(key: Measurement): List<Pair<Float, Float>>

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
