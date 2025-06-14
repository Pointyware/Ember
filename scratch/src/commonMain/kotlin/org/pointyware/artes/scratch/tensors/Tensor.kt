package org.pointyware.artes.scratch.tensors

fun absoluteIndex(dimensions: IntArray, indices: IntArray): Int {
    require(indices.size == dimensions.size) { "Indices must match the tensor order." }
    return indices.foldIndexed(0) { index, acc, offset -> acc * dimensions[index] + offset }
}

/**
 * A tensor is a generalization of vectors and matrices to higher dimensions.
 */
interface Tensor {

    val dimensions: IntArray
    val order: Int get () = dimensions.size
    val totalSize: Int get() = dimensions.fold(1) { acc, dim -> acc * dim }

    val isScalar: Boolean get() = order == 0
    val isVector: Boolean get() = order == 1
    val isMatrix: Boolean get() = order == 2
    val isTensor: Boolean get() = order > 2

    operator fun set(index: Int, value: Double)
    operator fun get(index: Int): Double

    /**
     * This order of indices provided by this iterator is row-major, meaning that the last
     * index changes the fastest.
     *
     * The following tensor will index in the order A, B, C, D:
     * ```
     * | A  B  |
     * | C  D  |
     * ```
     *
     * The returned iterator reuses the same list of indices, so it is not thread-safe and should
     * not be modified or stored.
     */
    val indices: Iterator<IntArray>
        get() = object : Iterator<IntArray> {
            private val dimensions: IntArray = this@Tensor.dimensions
            private val indexList = IntArray(dimensions.size).apply {
                // Prime first index with -1
                this[dimensions.size - 1] = -1
            }
            private var finished = false

            override fun hasNext(): Boolean {
                return !finished
            }

            override fun next(): IntArray {
                if (finished) throw NoSuchElementException("No more indices available.")

                // Starting at right-most index
                for (axis in indexList.indices.reversed()) {
                    // If we can increment this axis, do so and break
                    if (indexList[axis] < dimensions[axis] - 1) {
                        indexList[axis]++
                        break
                    } else { // Otherwise, reset this axis and attempt to increment the next axis
                        indexList[axis] = 0
                        // If we are at the left-most axis, we have finished iterating
                        if (axis == 0) finished = true
                    }
                }

                return indexList
            }
        }

    val values: Iterator<Double>
        get() = object : Iterator<Double> {
            private val indexIterator = indices
            override fun hasNext(): Boolean = indexIterator.hasNext()
            override fun next(): Double {
                return get(indexIterator.next())
            }
        }

    operator fun set(indices: IntArray, value: Double) {
        set(absoluteIndex(dimensions, indices), value)
    }
    operator fun get(indices: IntArray): Double {
        return get(absoluteIndex(dimensions, indices))
    }

    /**
     * Performs element-wise multiplication or matrix multiplication depending on the context.
     */
    operator fun times(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) { "Tensors must have the same dimensions for element-wise multiplication." }
        return SimpleTensor(dimensions).apply {
            for (i in 0 until totalSize) {
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
    operator fun plus(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) { "Tensors must have the same dimensions for addition." }
        return SimpleTensor(dimensions).apply {
            for (i in 0 until totalSize) {
                this[i] = this@Tensor[i] + other[i]
            }
        }
    }

    /**
     * Applies a function to each element of the tensor, modifying it in place.
     */
    fun mapEach(function: (Double)->Double) {
        for (index in indices) {
            this[index] = function(this[index])
        }
    }

    /**
     * Applies a function to each element of the tensor, modifying it in place.
     */
    fun mapEachIndexed(function: (IntArray, Double)->Double) {
        for (index in indices) {
            this[index] = function(index, this[index])
        }
    }
}
