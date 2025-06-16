package org.pointyware.artes.scratch.tensors

import org.junit.Test
import kotlin.math.exp
import kotlin.test.assertContentEquals

class TensorArithmeticUnitTest {

    @Test
    fun scalar_addition_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            2, 2
        )
        val tensor2 = Tensor.from(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            2, 2
        )
        val result = tensor1 + tensor2

        val expected = Tensor.from(
            doubleArrayOf(2.0, 3.0, 4.0, 5.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun scalar_subtraction_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(5.0, 6.0, 7.0, 8.0),
            4, 1
        )
        val tensor2 = Tensor.from(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            4, 1
        )
        val result = tensor1 - tensor2

        val expected = Tensor.from(
            doubleArrayOf(4.0, 5.0, 6.0, 7.0),
            4, 1
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(5.0, 6.0, 7.0, 8.0),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun scalar_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            2, 2
        )
        val scalar = 2.0
        val result = tensor1 * scalar

        val expected = Tensor.from(
            doubleArrayOf(2.0, 4.0, 6.0, 8.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }
}
