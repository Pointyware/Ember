package org.pointyware.artes.scratch.tensors

/**
 * This is just a regular tensor without any learning capabilities.
 */
class SimpleTensor(
    override val dimensions: IntArray,
    initializer: Double = 0.0,
): Tensor {
    private val data: DoubleArray = DoubleArray(totalSize) {
        initializer
    }

    override operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    override operator fun get(index: Int): Double {
        return data[index]
    }

    companion object {
        fun from(data: Double): SimpleTensor {
            return SimpleTensor(intArrayOf(), data)
        }

        fun from(vector: DoubleArray): SimpleTensor {
            return SimpleTensor(intArrayOf(vector.size)).apply {
                for (i in vector.indices) {
                    this[i] = vector[i]
                }
            }
        }

        fun from(vararg dimensions: Int): SimpleTensor {
            return SimpleTensor(dimensions)
        }
    }
}
