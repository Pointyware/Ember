package org.pointyware.ember.training.entities

import org.pointyware.ember.entities.DataList
import org.pointyware.ember.entities.ObjDataList
import kotlin.time.ExperimentalTime

private const val DEFAULT_MAX = 10f

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
@OptIn(ExperimentalTime::class)
class SequentialStatistics(
    override val measurements: List<Measurement> = listOf(Measurement.Loss)
): EpochStatistics, BatchStatistics, SampleStatistics {

    private val measurementsSet = measurements.toSet()

    private val errorMeasure = Measurement.Loss

    private val measures = measurements.associate { measurement ->
        measurement to ObjDataList("", 0f, 0f, 0f, 0f)
    }.toMutableMap<Measurement, DataList<*, Float>>()
    // private val epochMeasures = mutableMapOf<Measurement, DataList<Int, Float>>()
    // private val batchMeasures = mutableMapOf<Measurement, DataList<Pair<Int, Int>, Float>>()
    // private val sampleMeasures = mutableMapOf<Measurement, DataList<Triple<Int, Int, Int>, Float>>()
    override fun measurementMaximum(key: Measurement): Float {
        val measure = measures[key] ?: throw IllegalArgumentException("Measurement key not recognized: $key")
        return measure.max
    }

    override val measurementsMax: Float
        get() = measures.maxOf { it.value.max }
    override val epochCount: Int
        get() = measures.size

    override fun <I, O> data(key: Measurement): DataList<I, O> {
        val dataList = measures[key] ?: throw IllegalArgumentException("Measurement key not recognized: $key")
        @Suppress("UNCHECKED_CAST")
        return dataList as DataList<I, O>
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
        // TODO: create separate statistics for each measurement to match cadence/collection characteristics?
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
