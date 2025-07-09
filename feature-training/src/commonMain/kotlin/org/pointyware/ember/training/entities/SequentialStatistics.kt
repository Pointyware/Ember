package org.pointyware.ember.training.entities

/**
 * This [Statistics] type collects information for a [SequentialNetwork][org.pointyware.ember.entities.networks.SequentialNetwork].
 */
class SequentialStatistics(
): Statistics {

    val errorSamples: MutableList<Pair<Int, Float>> = mutableListOf()

    private var epochError = 0f
    override fun onEpochStart() {
        epochError = 0f
    }

    private var batchError = 0f
    override fun onBatchStart() {
        batchError = 0f
    }

    private var sampleError = 0f
    override fun onSampleStart() {
        sampleError = 0f
    }

    override fun onCost(cost: Float) {
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
