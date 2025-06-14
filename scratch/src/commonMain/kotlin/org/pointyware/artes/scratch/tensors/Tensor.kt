package org.pointyware.artes.scratch.tensors

fun absoluteIndex(dimensions: IntArray, indices: IntArray): Int {
    require(indices.size == dimensions.size) { "Indices must match the tensor order." }
    return indices.foldIndexed(0) { index, acc, offset -> acc * dimensions[index] + offset }
}

/**
 * An interface for iterating over tensors, allowing for element-wise operations.
 */
interface TensorIterator {
    /**
     * Applies a function to each element of the tensor, modifying it in place.
     */
    fun mapEach(function: (Double)->Double): Tensor

    /**
     * Applies an indexed function to each element of the tensor, modifying it in place.
     */
    fun mapEachIndexed(function: (IntArray, Double)->Double): Tensor

    /**
     * Applies a flat-indexed function to each element of the tensor, modifying it in place.
     *
     * The flat index is the linear index in the flattened array representation of the tensor.
     */
    fun mapEachFlatIndexed(function: (Int, Double)->Double): Tensor
}

/**
 * A tensor is a generalization of vectors and matrices to higher dimensions.
 */
data class Tensor(
    val dimensions: IntArray,
): TensorIterator {
    init {
        require(dimensions.size >= 0) { "Dimensions must be non-negative." }
    }
    val data: DoubleArray = DoubleArray(totalSize)

    val order: Int get () = dimensions.size
    val totalSize: Int get() = dimensions.fold(1) { acc, dim -> acc * dim }
    val basisSize: Int get() = dimensions.lastOrNull() ?: 1




    val isScalar: Boolean get() = order == 0
    val isVector: Boolean get() = order == 1
    val isMatrix: Boolean get() = order == 2
    val isTensor: Boolean get() = order > 2

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

    operator fun get(index: Int): Double {
        return data[index]
    }
    operator fun set(index: Int, value: Double) {
        data[index] = value
    }
    operator fun get(indices: IntArray): Double {
        return get(absoluteIndex(dimensions, indices))
    }
    operator fun set(indices: IntArray, value: Double) {
        set(absoluteIndex(dimensions, indices), value)
    }
    operator fun get(vararg indices: Int): Double {
        return get(absoluteIndex(dimensions, indices))
    }
    operator fun set(vararg indices: Int, value: Double) {
        set(absoluteIndex(dimensions, indices), value)
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEach(function: (value:Double)->Double): Tensor {
        for (index in indices) {
            this[index] = function(this[index])
        }
        return this
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEachIndexed(function: (indices:IntArray, value:Double)->Double): Tensor {
        for (index in indices) {
            this[index] = function(index, this[index])
        }
        return this
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEachFlatIndexed(function: (index:Int, value:Double) -> Double): Tensor {
        for (i in 0 until totalSize) {
            this[i] = function(i, this[i])
        }
        return this
    }

    // region Scalar Arithmetic operations 🧮

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun plus(scalar: Double): Tensor {
        return shape(*dimensions).mapEachFlatIndexed { _, value -> value + scalar }
    }

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun minus(scalar: Double): Tensor {
        return shape(*dimensions).mapEachFlatIndexed { _, value -> value - scalar }
    }

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun times(scalar: Double): Tensor {
        return shape(*dimensions).mapEachFlatIndexed { _, value -> value * scalar }
    }

    /**
     * Performs element-wise division of this tensor by the given [scalar].
     */
    operator fun div(scalar: Double): Tensor {
        return shape(*dimensions).mapEachFlatIndexed { _, value -> value / scalar }
    }

    // endregion

    // region Tensor Arithmetic operations ➕➖✖️➗

    /**
     * Adds two tensors element-wise. The tensors must have the same shape.
     */
    operator fun plus(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) { "Tensors must have the same dimensions for addition." }
        return shape(*dimensions).mapEachFlatIndexed { index, _ ->
            this[index] + other[index]
        }
    }

    /**
     * Performs element-wise multiplication or matrix multiplication depending on the context.
     */
    operator fun times(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) { "Tensors must have the same dimensions for element-wise multiplication." }
        return shape(*dimensions).mapEachFlatIndexed { index, _ ->
            this[index] * other[index]
        }
    }

    /**
     * Performs matrix multiplication, assuming both tensors are matrices with compatible dimensions.
     */
    fun matrixMultiply(other: Tensor): Tensor {
        require(isMatrix && other.isMatrix) { "Both tensors must be matrices for matrix multiplication." }
        require(dimensions[1] == other.dimensions[0]) { "Matrix dimensions do not match for multiplication." }

        val m = dimensions[0]
        val n = dimensions[1]
        val p = other.dimensions[1]

        return shape(m, p).mapEachIndexed { indices, value ->
            val i = indices[0]
            val j = indices[1]
            var sum = 0.0
            for (k in 0 until n) {
                sum += this[i, k] * other[k, j]
            }
            value + sum
        }
    }

    // endregion

    companion object {
        fun from(vector: DoubleArray): Tensor {
            return Tensor(intArrayOf(vector.size)).apply {
                for (i in vector.indices) {
                    this[i] = vector[i]
                }
            }
        }

        fun shape(vararg dimensions: Int): Tensor  = from(*dimensions)
        @Deprecated("Use Tensor.shape instead", ReplaceWith("Tensor.shape(*dimensions)"))
        fun from(vararg dimensions: Int): Tensor {
            return Tensor(dimensions)
        }
    }
}

fun scalar(value: Double): Tensor {
    return Tensor.shape().mapEach { value }
}

fun vector(length: Int, initializer: (Int)->Double = { 0.0 }): Tensor {
    return Tensor.shape(length).mapEachIndexed { indices, _ -> initializer(indices[0]) }
}

fun matrixOf(rows: Int, columns: Int, initializer: (Int,Int)->Double = { _, _ -> 0.0 }): Tensor {
    require(rows > 0 && columns > 0) { "Matrix dimensions must be positive." }
    return Tensor.shape(rows, columns).mapEachIndexed { indices, _ ->
        initializer(indices[0], indices[1])
    }
}
