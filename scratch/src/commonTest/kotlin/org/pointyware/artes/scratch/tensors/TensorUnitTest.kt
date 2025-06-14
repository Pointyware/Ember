package org.pointyware.artes.scratch.tensors

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class TensorUnitTest {


    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun values_iterator_returns_indices_in_row_major_order() {
        // Given a 6D tensor with dimensions 2x3x4x5x6x7
        val tensor = SimpleTensor(intArrayOf(2, 3, 1, 1, 1, 2))

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
    }
}
