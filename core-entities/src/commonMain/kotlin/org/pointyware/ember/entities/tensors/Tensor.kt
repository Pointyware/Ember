package org.pointyware.ember.entities.tensors

import org.pointyware.ember.entities.Marsaglia
import kotlin.math.max

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
    fun mapEach(function: (Float)->Float): Tensor

    /**
     * Applies an indexed function to each element of the tensor, modifying it in place.
     */
    fun mapEachIndexed(function: (IntArray, Float)->Float): Tensor

    /**
     * Applies a flat-indexed function to each element of the tensor, modifying it in place.
     *
     * The flat index is the linear index in the flattened array representation of the tensor.
     */
    fun mapEachFlatIndexed(function: (Int, Float)->Float): Tensor

    val flatIndices: Iterator<Int>
    val values: Iterator<Float>

    fun max(function: (Float) -> Float): Float {
        val iterator = values
        var max = iterator.next()
        while (iterator.hasNext()) max = max(max, function(iterator.next()))
        return max
    }
}

/**
 * A tensor is a generalization of vectors and matrices to higher dimensions.
 */
data class Tensor(
    val dimensions: IntArray,
): TensorIterator {
    init {
        require(dimensions.all { it > 0 }) { "Dimensions must be positive." }
    }
    val data: FloatArray = FloatArray(totalSize)

    val order: Int get () = dimensions.size
    val totalSize: Int get() = dimensions.fold(1) { acc, dim -> acc * dim }
    val shapeString: String
        get() = dimensions.joinToString(prefix = "[", postfix = "]", separator = ", ")

    /**
     * The size of the last dimension. For scalars with empty dimensions, returns 1.
     */
    val lastDimensionSize: Int get() = dimensions.lastOrNull() ?: 1

    /**
     * The area of the foundation of the tensor, which is the accumulated size of all dimensions
     * except the last one. This is useful for normalizing values across a tensor.
     */
    val area: Int get() = totalSize / lastDimensionSize

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
            private val indexList = IntArray(dimensions.size)
            private val flatIterator = flatIndices

            override fun hasNext(): Boolean = flatIterator.hasNext()

            override fun next(): IntArray {
                var rankIndex = flatIterator.next()
                // Starting at right-most index
                for (axis in indexList.indices.reversed()) {
                    indexList[axis] = rankIndex % dimensions[axis]
                    rankIndex /= dimensions[axis]
                }

                return indexList
            }
        }

    override val flatIndices: Iterator<Int>
        get() = object : Iterator<Int> {
            private var currentIndex = 0
            private val totalIndices = totalSize

            override fun hasNext(): Boolean {
                return currentIndex < totalIndices
            }

            override fun next(): Int {
                if (!hasNext()) throw NoSuchElementException("No more indices available.")
                return currentIndex++
            }
        }

    /**
     * An iterator that provides the indices in reverse order, which is useful for
     * iterating over tensors in a way that the first index changes the fastest.
     *
     * This is a generalization of a matrix transpose operation, where the first index
     * changes the fastest, effectively swapping the first and last dimensions.
     */
    val inverseIndices: Iterator<IntArray>
        get() = object : Iterator<IntArray> {
            private val dimensions: IntArray = this@Tensor.dimensions
            private val indexList = IntArray(dimensions.size)
            private var currentIndex = 0
            private val totalIndices = totalSize

            override fun hasNext(): Boolean {
                return currentIndex < totalIndices
            }

            override fun next(): IntArray {
                if (!hasNext()) throw NoSuchElementException("No more indices available.")

                var rankIndex = currentIndex++
                // Starting at left-most index
                for (axis in indexList.indices) {
                    indexList[axis] = rankIndex % dimensions[axis]
                    rankIndex /= dimensions[axis]
                }

                return indexList
            }
        }

    override val values: Iterator<Float>
        get() = object : Iterator<Float> {
            private val indexIterator = indices
            override fun hasNext(): Boolean = indexIterator.hasNext()
            override fun next(): Float {
                return get(*indexIterator.next()).toFloat()
            }
        }

    operator fun get(index: Int): Float {
        return data[index]
    }
    operator fun set(index: Int, value: Float) {
        data[index] = value
    }
    operator fun get(vararg indices: Int): Float { // TODO: convert signatures to Float instead of Double
        return get(absoluteIndex(dimensions, indices))
    }
    operator fun set(vararg indices: Int, value: Float) {
        set(absoluteIndex(dimensions, indices), value)
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEach(function: (value:Float)->Float): Tensor {
        for (index in flatIndices) {
            this[index] = function(this[index])
        }
        return this
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEachIndexed(function: (indices:IntArray, value:Float)->Float): Tensor {
        for (index in indices) {
            this.set(indices = index, function(index, this.get(*index)))
        }
        return this
    }

    @Suppress("OVERRIDE_BY_INLINE")
    override inline fun mapEachFlatIndexed(function: (index:Int, value:Float) -> Float): Tensor {
        for (i in flatIndices) {
            this[i] = function(i, this[i])
        }
        return this
    }

    fun assign(other: Tensor) {
        require(dimensions.contentEquals(other.dimensions)) { "Dimensions must match. Found [${shapeString}], and [${other.shapeString}]" }
        other.data.copyInto(data)
    }

    // region Scalar Arithmetic operations 🧮

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun plus(scalar: Float): Tensor {
        return zeros(*dimensions).mapEachFlatIndexed { index, _ -> this[index] + scalar }
    }

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun minus(scalar: Float): Tensor {
        return zeros(*dimensions).mapEachFlatIndexed { index, _ -> this[index] - scalar }
    }

    /**
     * Performs element-wise addition of this tensor by the given [scalar].
     */
    operator fun times(scalar: Float): Tensor {
        return zeros(*dimensions).mapEachFlatIndexed { index, _ -> this[index] * scalar }
    }

    /**
     * Performs in-place element-wise multiplication of this tensor by the given [scalar].
     */
    operator fun timesAssign(scalar: Float) {
        mapEach { value -> value * scalar }
    }

    /**
     * Performs element-wise division of this tensor by the given [scalar].
     */
    operator fun div(scalar: Float): Tensor {
        return zeros(*dimensions).mapEachFlatIndexed { index, _ -> this[index] / scalar }
    }

    // endregion

    // region Tensor Arithmetic operations ➕➖✖️➗

    /**
     * Adds two tensors element-wise. The tensors must have the same shape.
     */
    operator fun plus(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) {
            "Tensors must have the same dimensions for addition. Found [${shapeString}], and [${other.shapeString}]"
        }
        return zeros(*dimensions).mapEachFlatIndexed { index, _ ->
            this[index] + other[index]
        }
    }

    /**
     * Performs in-place addition of this tensor by the given [other] tensor.
     */
    operator fun plusAssign(other: Tensor) {
        require(dimensions.contentEquals(other.dimensions)) {
            "Tensors must have the same dimensions for addition. Found [${shapeString}], and [${other.shapeString}]"
        }
        mapEachFlatIndexed { index, value -> value + other[index] }
    }

    operator fun minus(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) {
            "Tensors must have the same dimensions for subtraction. Found [${shapeString}], and [${other.shapeString}]"
        }
        return zeros(*dimensions).mapEachFlatIndexed { index, _ ->
            this[index] - other[index]
        }
    }

    /**
     * Performs element-wise multiplication, .a.k.a. Hadamard product.
     */
    operator fun times(other: Tensor): Tensor {
        require(dimensions.contentEquals(other.dimensions)) {
            "Tensors must have the same dimensions for element-wise multiplication. " +
                    "Found [${shapeString}], and [${other.shapeString}]"
        }
        return zeros(*dimensions).mapEachFlatIndexed { index, _ ->
            this[index] * other[index]
        }
    }

    // endregion

    // region Matrix Arithmetic operations

    /**
     * Performs matrix multiplication, assuming both tensors are matrices with compatible dimensions.
     */
    fun matrixMultiply(other: Tensor): Tensor {
        require(isMatrix && other.isMatrix) {
            "Both tensors must be matrices for matrix multiplication. " +
                "Found [${shapeString}], and [${other.shapeString}]"
        }
        require(dimensions[1] == other.dimensions[0]) {
            "Matrix dimensions do not match for multiplication." +
                "Found [${shapeString}], and [${other.shapeString}]"
        }

        val m = dimensions[0]
        val n = dimensions[1]
        val p = other.dimensions[1]

        return zeros(m, p).mapEachFlatIndexed { index, value ->
            val row = index / p
            val column = index % p
            var sum = 0.0f
            for (k in 0 until n) {
                sum += this[row, k] * other[k, column]
            }
            value + sum
        }
    }

    fun transpose(): Tensor {
        require(isMatrix) { "Transpose is only defined for matrices." }
        val rows = dimensions[0]
        val columns = dimensions[1]
        return zeros(columns, rows).mapEachIndexed { (row, column), _ ->
            this[column, row]
        }
    }

    // endregion

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tensor

        if (!dimensions.contentEquals(other.dimensions)) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dimensions.contentHashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "Tensor(dimensions=${dimensions.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ", "
        )}, data=${data.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ", "
        )})"
    }

    fun zero() {
        for (i in data.indices) data[i] = 0f
    }

    // endregion

    companion object {
        fun from(vector: FloatArray): Tensor {
            return Tensor(intArrayOf(vector.size)).apply {
                for (i in vector.indices) {
                    this[i] = vector[i]
                }
            }
        }

        /**
         * Creates a tensor from a [values] array and specified [dimensions].
         *
         * The [values] must have a length equal to the product of the [dimensions].
         */
        fun from(values: FloatArray, vararg dimensions: Int): Tensor {
            return Tensor(dimensions).mapEachFlatIndexed { index, _ -> values[index] }
        }

        /**
         * Creates a tensor with the specified dimensions, initialized to zero.
         */
        fun zeros(vararg dimensions: Int): Tensor {
            return Tensor(dimensions)
        }

        fun ones(dimensions: IntArray): Tensor {
            return Tensor(dimensions).mapEach { 1.0f }
        }

        /**
         * Creates a tensor with the specified dimensions, initialized with random values.
         *
         * The values are generated using a normal distribution with [mean] default 0 and
         * [stdDev] default 0.1.
         */
        fun random(mean: Float = 0.0f, stdDev: Float = 0.1f, vararg dimensions: Int): Tensor {
            return Tensor(dimensions).mapEach { _ -> mean + Marsaglia.getNormal().toFloat() * stdDev }
        }

        fun concat(attention: List<Tensor>): Tensor {
            val dimensionSum = attention.sumOf {
                require(it.dimensions.size == 2) { "All tensors must be 2-dimensional." }
                require(it.dimensions[1] == 1) { "The last dimension must be 1." }
                it.dimensions[0]
            }
            val result = zeros(dimensionSum, 1)
            var row = 0
            for (tensor in attention) {
                tensor.values.forEach {
                    result[row] = it
                    row++
                }
            }
            return result
        }
    }
}

/**
 * Multiplies a scalar with a tensor, returning a new tensor. Delegates to [Tensor.times] since
 * scalar multiplication is commutative.
 */
operator fun Float.times(tensor: Tensor): Tensor {
    return tensor * this
}

fun rowVector(vararg values: Float): Tensor {
    require(values.isNotEmpty()) { "At least one value is required to create a vector." }
    return Tensor.from(values, dimensions = intArrayOf(1, values.size))
}

fun columnVector(vararg values: Float): Tensor {
    require(values.isNotEmpty()) { "At least one value is required to create a vector." }
    return Tensor.from(values, dimensions = intArrayOf(values.size, 1))
}
