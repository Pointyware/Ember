package org.pointyware.artes.scratch.networks

import org.pointyware.artes.scratch.tensors.Tensor

/**
 * A network is collection of neurons that can be arranged in a wide variety of ways. Most often
 * they are arranged in layers, but they can also be arranged in more complex structures with
 * branches, skips, and other connections.
 */
interface Network {
    fun predict(input: Tensor): Tensor
}
