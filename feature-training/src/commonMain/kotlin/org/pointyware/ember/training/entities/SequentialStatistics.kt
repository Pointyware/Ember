package org.pointyware.ember.training.entities

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
class SequentialStatistics(
): Statistics {
    override val measurements: List<Measurement>
        get() = TODO("Not yet implemented")

    override fun measurementMaximum(key: Measurement): Float {
        TODO("Not yet implemented")
    }

    override val measurementsMax: Float
        get() = TODO("Not yet implemented")
    override val epochCount: Int
        get() = TODO("Not yet implemented")

    override fun data(it: Measurement): List<Pair<Float, Float>> {
        TODO("Not yet implemented")
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

    override fun onBatchEnd(batch: List<Exercise>) {
        epochError += batchError
    }

    override fun onEpochEnd(epoch: Int) {
        // TODO: report epoch error
    }
}
