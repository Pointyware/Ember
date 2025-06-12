package org.pointyware.artes.scratch.tensors

/**
 * A tensor is a generalization of vectors and matrices to higher dimensions.
 */
interface Tensor {

    val dimensions: List<Int>
    val order: Int get () = dimensions.size

    val isScalar: Boolean get() = order == 0
    val isVector: Boolean get() = order == 1
    val isMatrix: Boolean get() = order == 2
    val isTensor: Boolean get() = order > 2

    operator fun set(index: Int, value: Double)
    operator fun get(index: Int): Double

    /**
     * Performs element-wise multiplication or matrix multiplication depending on the context.
     */
    operator fun times(other: Tensor): Tensor {
        require(dimensions == other.dimensions) { "Tensors must have the same dimensions for element-wise multiplication." }
        return SimpleTensor(dimensions).apply {
            for (i in 0 until size) {
                this[i] = this@Tensor[i] * other[i]
            }
        }
    }

    fun matrixMultiply(other: Tensor): Tensor {
        require(isMatrix && other.isMatrix) { "Both tensors must be matrices for matrix multiplication." }
        require(dimensions[1] == other.dimensions[0]) { "Matrix dimensions do not match for multiplication." }

        val m = dimensions[0]
        val n = dimensions[1]
        val p = other.dimensions[1]
        TODO("Implement matrix multiplication")

    }

    /**
     * Adds two tensors element-wise. The tensors must have the same shape.
     */
    operator fun plus(other: Tensor): Tensor

    fun computeLoss(): Double
}
