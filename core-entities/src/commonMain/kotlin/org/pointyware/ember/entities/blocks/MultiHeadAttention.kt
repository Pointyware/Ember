package org.pointyware.ember.entities.blocks

import org.pointyware.ember.entities.tensors.Tensor

/**
 * MHA and MMHA are composed of the following operations:
 * - Linear value input (|V| = d_v)
 * - Linear key input (|K| = d_k)
 * - Linear query input (|Q| = d_k)
 * - Scaled Dot Product Attention x Head Count (h)
 * - Concat
 * - Linear
 *
 * @param heads List of attention heads
 * @param queryProjections List of projection matrices for the query
 * @param keyProjections List of projection matrices for the key
 * @param valueProjections List of projection matrices for the value
 */
class MultiHeadAttention(
    val heads: List<ScaledDotProductAttention>,
    val queryProjections: List<Tensor>,
    val keyProjections: List<Tensor>,
    val valueProjections: List<Tensor>,
): Attention {
    init {
        val headCount = heads.size
        require(headCount > 0) { "The number of heads must be greater than 0." }
        val dQ = queryProjections.size
        val dK = keyProjections.size
        val dV = valueProjections.size
        require(headCount == dQ) { "The number of heads ($headCount) must match the number of query projections ($dQ)." }
        require(headCount == dK) { "The number of heads ($headCount) must match the number of key projections ($dK)." }
        require(headCount == dV) { "The number of heads ($headCount) must match the number of value projections ($dV)." }
    }

    override val parameterCount: Int
        get() = heads.sumOf { it.parameterCount } + queryProjections.sumOf { it.totalSize } +
                keyProjections.sumOf { it.totalSize } + valueProjections.sumOf { it.totalSize }

    override fun calculate(query: Tensor, key: Tensor, value: Tensor): Tensor {
        val projectedQuery = queryProjections.map { it.matrixMultiply(query) }
        val projectedKey = keyProjections.map { it.matrixMultiply(key) }
        val projectedValue = valueProjections.map { it.matrixMultiply(value) }
        val attention = heads.mapIndexed { index, attention ->
            attention.calculate(projectedQuery[index], projectedKey[index], projectedValue[index])
        }
        return Tensor.concat(attention)
    }
}
