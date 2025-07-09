package org.pointyware.ember.training.entities

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
class SequentialStatistics(
): Statistics {

    val errorSamples: MutableList<Pair<Int, Float>> = mutableListOf()

    private var epochError = 0.0
    override fun onEpochStart() {
        epochError = 0.0
    }

    private var batchError = 0.0
    override fun onBatchStart() {
        batchError = 0.0
    }

    private var sampleError = 0.0
    override fun onSampleStart() {
        sampleError = 0.0
    }

    override fun onCost(cost: Double) {
        sampleError += cost
    }

    override fun onGradient() {
        // TODO: report gradient?
    }

    override fun onSampleEnd() {
        batchError += sampleError
    }

    override fun onBatchEnd() {
        epochError += batchError
    }

    override fun onEpochEnd() {
        // TODO: report epoch error
    }
}
