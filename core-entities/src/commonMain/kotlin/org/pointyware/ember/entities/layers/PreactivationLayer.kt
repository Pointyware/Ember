package org.pointyware.ember.entities.layers

import org.pointyware.ember.entities.tensors.Tensor

/**
 *
 */
interface PreactivationLayer: Layer {
    fun preactivation(input: Tensor, output: Tensor)
}
