package org.pointyware.ember.entities.blocks

import org.pointyware.ember.entities.activations.SoftArgMax
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.math.pow

/**
 *
 * - Scaled Dot Product Attention x Head Count (h)
 *   - MatMul Q and K
 *   - Scale by sqrt(d_k)
 *   - Optional Mask
 *   - SoftMax
 *   - MatMul SoftMax output with V
 *
 * @param dimensionKey The dimension of the key and query inputs.
 * @param dimensionValue The dimension of the value input.
 * @param mask An optional mask to apply to the attention scores.
 */
class ScaledDotProductAttention(
    val dimensionKey: Int,
    val dimensionValue: Int,
    val mask: Tensor? = null
): Attention {

    private val softmax = SoftArgMax()

    private val scalingFactor = dimensionKey.toDouble().pow(-0.5).toFloat()
    override fun invoke(query: Tensor, key: Tensor, value: Tensor): Tensor {
        val matMul = query.matrixMultiply(key)
        matMul.mapEach { it * scalingFactor }
        if (mask != null) {
            matMul.mapEachFlatIndexed { index, value -> value * mask[index] }
        }
        val softmax = softmax.calculate(matMul)
        return softmax.matrixMultiply(value)
    }
}
