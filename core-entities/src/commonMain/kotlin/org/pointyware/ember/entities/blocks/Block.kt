package org.pointyware.ember.entities.blocks

/**
 * A neural network block is a sub-unit of a complex neural network
 * that performs a specific function.
 */
interface Block {
    val parameterCount: Int
    // fun calculate(context: Map<Long, Tensor>): Tensor
}
