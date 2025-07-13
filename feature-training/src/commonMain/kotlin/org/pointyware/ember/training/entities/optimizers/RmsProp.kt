package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.entities.layers.Layer
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.Exercise

/**
 * Implemented per lecture slides: https://www.cs.toronto.edu/~tijmen/csc321/slides/lecture_slides_lec6.pdf
 */
class RmsProp(

): Optimizer {
    override fun batch(cases: List<Exercise>): List<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override fun update(layer: Layer, weightGradients: Tensor, biasGradients: Tensor) {
        TODO("Not yet implemented")
    }
}
