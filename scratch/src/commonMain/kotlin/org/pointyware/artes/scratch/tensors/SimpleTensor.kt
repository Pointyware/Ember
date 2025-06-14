package org.pointyware.artes.scratch.tensors

/**
 * This is just a regular tensor without any learning capabilities.
 */
class SimpleTensor(
    override val dimensions: IntArray,
    initializer: Double = 0.0,
): Tensor {
    val size: Int = dimensions.fold(1) { acc, dim -> acc * dim }

    private val data: DoubleArray = DoubleArray(size) {
        initializer
    }

    override operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    override operator fun get(index: Int): Double {
        return data[index]
    }

//    override val dimensions: List<Int> = run {
//            val dims = mutableListOf<Int>()
//            var current: Any? = data
//            while (current is Array<*>) {
//                dims.add(current.size)
//                current = if (current.isNotEmpty()) current[0] else null
//            }
//            dims.toList()
//        }
//

    override fun plus(other: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    override fun times(other: Tensor): Tensor {
        TODO("Not yet implemented")
    }

    override fun computeLoss(): Double {
        TODO("Not yet implemented")
    }

    companion object {
        fun from(data: Double): SimpleTensor {
            return SimpleTensor(emptyList(), data)
        }

        fun from(vector: DoubleArray): SimpleTensor {
            return SimpleTensor(listOf(vector.size)).apply {
                for (i in vector.indices) {
                    this[i] = vector[i]
                }
            }
        }

        fun from(width: Int): SimpleTensor {
            return SimpleTensor(listOf(width))
        }

        fun from(width: Int, height: Int): SimpleTensor {
            return SimpleTensor(listOf(width, height))
        }
    }
}
