package org.pointyware.ember.training.entities

private const val DEFAULT_MAX = 10f

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
class SequentialStatistics(
    override val updatePeriod: Int = 10e3.toInt(),
): Statistics {

    private val errorMeasure = Measurement("Error", Measurement.Subject.Error)
    private val accuracy = mutableListOf<Pair<Float, Float>>()
    override val measurements: List<Measurement>
        get() = listOf(errorMeasure)

    private var maximum = 0f
    private var maxDirty = true
    override fun measurementMaximum(key: Measurement): Float {
        if (key != errorMeasure)
            throw IllegalArgumentException("Measurement key not recognized: $key")
        if (maxDirty) {
            maximum = if (accuracy.isEmpty()) {
                DEFAULT_MAX
            } else {
                accuracy.maxOf { it.second }
            }
        }
        return maximum
    }

    override val measurementsMax: Float
        get() = measurementMaximum(errorMeasure)
    override val epochCount: Int
        get() = accuracy.size

    override fun data(key: Measurement): List<Pair<Float, Float>> {
        if (key != errorMeasure)
            throw IllegalArgumentException("Measurement key not recognized: $key")
        return accuracy
    }

    val errorSamples: MutableList<Pair<Int, Float>> = mutableListOf()

    private var epochError = 0.0
    override fun onEpochStart(epoch: Int) {
        epochError = 0.0
    }

    private var batchError = 0.0
    override fun onBatchStart(batch: List<Exercise>) {
        batchError = 0.0
    }

    private var sampleError = 0.0
    override fun onSampleStart(sample: Exercise) {
        sampleError = 0.0
    }

    override fun onCost(cost: Double) {
        sampleError += cost
    }

    override fun onGradient() {
        // TODO: report gradient?
    }

    override fun onSampleEnd(sample: Exercise) {
        batchError += sampleError
    }

    private var batchSize = 0
    override fun onBatchEnd(batch: List<Exercise>) {
        epochError += batchError
        batchSize = batch.size
    }

    override fun onEpochEnd(epoch: Int) {
        val averageError = epochError / batchSize
        accuracy.add(epoch.toFloat() to epochError.toFloat())
    }
}
