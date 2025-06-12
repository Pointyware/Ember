package org.pointyware.artes.scratch.networks

import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A sequential network is a series of layers that are applied sequentially,
 * without branches or skips.
 */
class SequentialNetwork(
    val layers: List<LinearLayer>,
) : Network {
    override fun predict(input: Tensor): Tensor {
        return layers.fold(input) { acc, layer ->
            layer.forward(acc)
        }
    }
}
