package org.pointyware.artes.scratch.tensors

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class TensorUnitTest {


    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun tensor_creation_with_dimensions() {
        assertFailsWith<IllegalArgumentException> {
            Tensor(intArrayOf(2, 3, 1, 1, 1, -2))
        }

        val dimensions = intArrayOf(2, 3, 1, 1, 1, 2)
        val tensor = Tensor(dimensions)
        assertContentEquals(dimensions, tensor.dimensions)
    }

    @Test
    fun values_iterator_returns_indices_in_row_major_order() {
        // Given a 6D tensor with dimensions 2x3x1x1x1x2 => count = 12
        val tensor = Tensor(intArrayOf(2, 3, 1, 1, 1, 2))

        // When we iterate over the indices
        val valuesIterator = tensor.indices

        // Then the indices should be in row-major order

        listOf(
            intArrayOf(0, 0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 1),
            intArrayOf(0, 1, 0, 0, 0, 0),
            intArrayOf(0, 1, 0, 0, 0, 1),
            intArrayOf(0, 2, 0, 0, 0, 0),
            intArrayOf(0, 2, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 0, 0),
            intArrayOf(1, 0, 0, 0, 0, 1),
            intArrayOf(1, 1, 0, 0, 0, 0),
            intArrayOf(1, 1, 0, 0, 0, 1),
            intArrayOf(1, 2, 0, 0, 0, 0),
            intArrayOf(1, 2, 0, 0, 0, 1)
        ).forEach { expected ->
            assertContentEquals(expected, valuesIterator.next())
        }

        val hasNext = valuesIterator.hasNext()
        assertFalse(hasNext,
            "Iterator should not have more elements after exhausting all indices;" +
                    " found: " +
                    if (hasNext) valuesIterator.next().joinToString(separator = ",")
                    else ""
        )
    }
}
