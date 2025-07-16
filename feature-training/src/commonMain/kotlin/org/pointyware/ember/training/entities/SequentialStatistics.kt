package org.pointyware.ember.training.entities

import kotlin.time.ExperimentalTime

private const val DEFAULT_MAX = 10f

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
@OptIn(ExperimentalTime::class)
class SequentialStatistics(
): EpochStatistics, BatchStatistics, SampleStatistics {

    private val errorMeasure = Measurement.Loss
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

    private var trainedSamples = 0
    private var epochError = 0.0
    override fun onEpochStart(epoch: Int) {
        epochError = 0.0
        trainedSamples = 0
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

    override fun onBatchEnd(batch: List<Exercise>) {
        epochError += batchError
        trainedSamples += batch.size
    }

    var lastEpoch = 0
    override fun onEpochEnd(epoch: Int) {
        lastEpoch = epoch
        val averageError = epochError / trainedSamples
        accuracy.add(epoch.toFloat() to averageError.toFloat())
    }

    override fun createSnapshot(): Snapshot {
        return Snapshot(
            epoch = lastEpoch,
            measurements = mapOf(
                errorMeasure to accuracy.toList()
            )
        )
    }
}
