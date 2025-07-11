package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise
import org.pointyware.ember.training.entities.Measurement

class AdaGrad: StatisticalOptimizer {
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

    override fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
        TODO("Not yet implemented")
    }
}
