package org.pointyware.ember.tensors

import org.pointyware.ember.entities.tensors.Tensor
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
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
    fun indices_iterator_returns_indices_in_row_major_order() {
        // Given a 6D tensor with dimensions 2x3x1x1x1x2 => count = 12
        val tensor = Tensor(intArrayOf(2, 3, 1, 1, 1, 2))

        // When we iterate over the indices
        val indicesIterator = tensor.indices

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
            assertContentEquals(expected, indicesIterator.next())
        }

        val hasNext = indicesIterator.hasNext()
        assertFalse(hasNext,
            "Iterator should not have more elements after exhausting all indices;" +
                    " found: " +
                    if (hasNext) indicesIterator.next().joinToString(separator = ",")
                    else ""
        )
    }

    @Test
    fun flat_indices_iterator_returns_flat_indices_in_order() {
        // Given a 6D tensor with dimensions 2x3x1x1x1x2 => count = 12
        val tensor = Tensor(intArrayOf(2, 3, 1, 1, 1, 2))

        // When we iterate over the flat indices
        val flatIndicesIterator = tensor.flatIndices

        // Then the flat indices should be in row-major order
        listOf(
            0, 1, 2, 3, 4, 5,
            6, 7, 8, 9, 10, 11
        ).forEach { expected ->
            assertEquals(expected, flatIndicesIterator.next())
        }

        val hasNext = flatIndicesIterator.hasNext()
        assertFalse(hasNext,
            "Iterator should not have more elements after exhausting all indices;" +
                    " found: " +
                    if (hasNext) flatIndicesIterator.next().toString()
                    else ""
        )
    }

    @Test
    fun inverse_indices_iterator_returns_indices_in_column_major_order() {
        // Given a 6D tensor with dimensions 2x3x1x1x1x2 => count = 12
        val tensor = Tensor(intArrayOf(2, 3, 1, 1, 1, 2))

        // When we iterate over the inverse indices
        val indicesIterator = tensor.inverseIndices

        // Then the indices should be in column-major order
        listOf(
            intArrayOf(0, 0, 0, 0, 0, 0),
            intArrayOf(1, 0, 0, 0, 0, 0),
            intArrayOf(0, 1, 0, 0, 0, 0),
            intArrayOf(1, 1, 0, 0, 0, 0),
            intArrayOf(0, 2, 0, 0, 0, 0),
            intArrayOf(1, 2, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0, 1),
            intArrayOf(1, 0, 0, 0, 0, 1),
            intArrayOf(0, 1, 0, 0, 0, 1),
            intArrayOf(1, 1, 0, 0, 0, 1),
            intArrayOf(0, 2, 0, 0, 0, 1),
            intArrayOf(1, 2, 0, 0, 0, 1)
        ).forEach { expected ->
            assertContentEquals(expected, indicesIterator.next())
        }

        val hasNext = indicesIterator.hasNext()
        assertFalse(hasNext,
            "Iterator should not have more elements after exhausting all indices;" +
                    " found: " +
                    if (hasNext) indicesIterator.next().joinToString(separator = ",")
                    else ""
        )
    }

    @Test
    fun transpose_returns_transposed_tensor() {
        // Given a 2D tensor
        val tensor = Tensor.from(
            values = floatArrayOf(
                1.0f, 2.0f, 3.0f,
                4.0f, 5.0f, 6.0f
            ),
            2, 3
        )

        // When we transpose
        val result = tensor.transpose()

        // Then the dimensions should be swapped
        assertContentEquals(intArrayOf(3, 2), result.dimensions)
        // And the values should be transposed
        val expectedTensor = Tensor.from(
            values = floatArrayOf(
                1.0f, 4.0f,
                2.0f, 5.0f,
                3.0f, 6.0f
            ),
            3, 2
        )
        assertContentEquals(expectedTensor.data, result.data)
    }
}
