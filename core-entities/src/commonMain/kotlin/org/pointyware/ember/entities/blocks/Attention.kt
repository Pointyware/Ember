package org.pointyware.ember.entities.blocks

import org.pointyware.ember.entities.tensors.Tensor

/**
 * An attention mechanism takes in query, key, and value tensors and returns a weighted
 * sum of the values, where the weights are calculated based on compatibility between
 * the query and key.
 */
interface Attention: Block {
    fun calculate(query: Tensor, key: Tensor, value: Tensor): Tensor
}
