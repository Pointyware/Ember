package org.pointyware.ember.entities.tensors

/**
 * A matrix represented as a list of lists of floats, backed by a [Tensor].
 */
class ListMatrix(
    val source: Tensor
): List<List<Float>> {
    override val size: Int
        get() = source.dimensions[0]

    override fun contains(element: List<Float>): Boolean {
        return indexOf(element) != -1
    }

    override fun containsAll(elements: Collection<List<Float>>): Boolean {
        for (element in elements) {
            if (!contains(element)) {
                return false
            }
        }
        return true
    }

    override fun get(index: Int): List<Float> {
        return ListVector(source, index)
    }

    override fun indexOf(element: List<Float>): Int {
        row@for (i in indices) {
            val row = this[i]
            for (j in row.indices) {
                if (row[j] != element[j]) {
                    continue@row
                }
            }
            return i
        }
        return -1
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun iterator(): Iterator<List<Float>> {
        return object : Iterator<List<Float>> {
            private var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < size
            }

            override fun next(): List<Float> {
                if (!hasNext()) throw NoSuchElementException()
                return get(currentIndex++)
            }
        }
    }

    override fun lastIndexOf(element: List<Float>): Int {
        row@for (i in indices.reversed()) {
            val row = this[i]
            for (j in row.indices) {
                if (row[j] != element[j]) {
                    continue@row
                }
            }
            return i
        }
        return -1
    }

    override fun listIterator(): ListIterator<List<Float>> {
        return object : ListIterator<List<Float>> {
            private var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < size
            }

            override fun next(): List<Float> {
                if (!hasNext()) throw NoSuchElementException()
                return get(currentIndex++)
            }

            override fun hasPrevious(): Boolean {
                return currentIndex > 0
            }

            override fun previous(): List<Float> {
                if (!hasPrevious()) throw NoSuchElementException()
                return get(--currentIndex)
            }

            override fun nextIndex(): Int {
                return currentIndex
            }

            override fun previousIndex(): Int {
                return currentIndex - 1
            }
        }
    }

    override fun listIterator(index: Int): ListIterator<List<Float>> {
        if (index < 0 || index > size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        return object : ListIterator<List<Float>> {
            private var currentIndex = index

            override fun hasNext(): Boolean {
                return currentIndex < size
            }

            override fun next(): List<Float> {
                if (!hasNext()) throw NoSuchElementException()
                return get(currentIndex++)
            }

            override fun hasPrevious(): Boolean {
                return currentIndex > 0
            }

            override fun previous(): List<Float> {
                if (!hasPrevious()) throw NoSuchElementException()
                return get(--currentIndex)
            }

            override fun nextIndex(): Int {
                return currentIndex
            }

            override fun previousIndex(): Int {
                return currentIndex - 1
            }
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<List<Float>> {
        TODO("Not yet implemented")
    }
}
