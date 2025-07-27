package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.DataList
import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.BatchStatistics
import org.pointyware.ember.training.entities.EpochStatistics
import org.pointyware.ember.training.entities.Exercise
import org.pointyware.ember.training.entities.Measurement
import org.pointyware.ember.training.entities.SampleStatistics
import org.pointyware.ember.training.entities.Snapshot

class AdaGrad(
): MultiPassOptimizer, EpochStatistics, BatchStatistics, SampleStatistics {

    override val measurements: List<Measurement<Float>>
        get() = TODO("Not yet implemented")

    override fun measurementMaximum(key: Measurement<Float>): Float {
        TODO("Not yet implemented")
    }

    override val measurementsMax: Float
        get() = TODO("Not yet implemented")
    override val epochCount: Int
        get() = TODO("Not yet implemented")

    override fun createSnapshot(): Snapshot {
        TODO("Not yet implemented")
    }

    override fun data(key: Measurement<Float>): DataList<Float, Float> {
        TODO("Not yet implemented")
    }
    override fun onEpochStart(epoch: Int) {
        TODO("Not yet implemented")
    }

    override fun onBatchStart(batch: List<Exercise>) {
        TODO("Not yet implemented")
    }

    override fun onSampleStart(sample: Exercise) {
        TODO("Not yet implemented")
    }

    override fun onCost(cost: Double) {
        TODO("Not yet implemented")
    }

    override fun onGradient() {
        TODO("Not yet implemented")
    }

    override fun onSampleEnd(sample: Exercise) {
        TODO("Not yet implemented")
    }

    override fun onBatchEnd(batch: List<Exercise>) {
        TODO("Not yet implemented")
    }

    override fun onEpochEnd(epoch: Int) {
        TODO("Not yet implemented")
    }

    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override fun update(
        step: Int,
        epoch: Int,
        layer: Layer,
        weightGradients: Tensor,
        biasGradients: Tensor
    ) {
        TODO("Not yet implemented")
    }

    override fun passAgain(): Boolean {
        TODO("Not yet implemented")
    }
}
