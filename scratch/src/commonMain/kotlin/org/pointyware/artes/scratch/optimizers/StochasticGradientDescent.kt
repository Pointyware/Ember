package org.pointyware.artes.scratch.optimizers

import org.pointyware.artes.scratch.layers.Layer
import org.pointyware.artes.scratch.tensors.Tensor

class StochasticGradientDescent(
    val learningRate: Double
): Optimizer {

    override fun reset(layer: Layer) {
        TODO("Not yet implemented")
    }

    override fun update(layer: Layer, gradient: Tensor) {
        TODO("Not yet implemented")
    }
}
