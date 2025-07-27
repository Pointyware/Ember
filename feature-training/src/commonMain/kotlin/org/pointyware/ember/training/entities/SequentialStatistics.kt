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
    override val measurements: List<Measurement<Float>>,
): EpochStatistics, BatchStatistics, SampleStatistics {

    private val measures: MutableMap<Measurement<Float>, DataList<Float, Float>> = measurements.associateWith { measurement ->
        ObjDataList("", 0f, 0f, 0f, 0f)
    }.toMutableMap()

    // private val epochMeasures = mutableMapOf<Measurement, DataList<Int, Float>>()
    // private val batchMeasures = mutableMapOf<Measurement, DataList<Pair<Int, Int>, Float>>()
    // private val sampleMeasures = mutableMapOf<Measurement, DataList<Triple<Int, Int, Int>, Float>>()
    override fun measurementMaximum(key: Measurement<Float>): Float {
        val measure = measures[key] ?: throw IllegalArgumentException("Measurement key not recognized: $key")
        return measure.max
    }

    override val measurementsMax: Float
        get() = measures.maxOf { it.value.max }
    override val epochCount: Int
        get() = measures.size

    override fun data(key: Measurement<Float>): DataList<Float, Float> {
        val dataList = measures[key] ?: throw IllegalArgumentException("Measurement key not recognized: $key")
        @Suppress("UNCHECKED_CAST")
        return dataList //  as DataList<I, O>
    }

    override fun onEpochStart(epoch: Int, context: ComputationContext) {
    }

    override fun onBatchStart(batch: List<Exercise>) {
    }

    override fun onSampleStart(sample: Exercise) {
    }

    override fun onCost(cost: Double) {
    }

    override fun onGradient() {
        // TODO: report gradient?
    }

    override fun onSampleEnd(sample: Exercise) {
    }

    override fun onBatchEnd(batch: List<Exercise>) {
    }

    var lastEpoch = 0
    override fun onEpochEnd(epoch: Int, context: ComputationContext) {
        lastEpoch = epoch

        val epochFloat = epoch.toFloat()
        measures.forEach { (measurement, dataList) ->
            val value = context.get(measurement.key)
            dataList.put(epochFloat, value)
        }
    }

    override fun createSnapshot(): Snapshot {

        return Snapshot(
            epoch = lastEpoch,
            measurements = measurements.associateWith { it -> measures[it]!! }
        )
    }
}
