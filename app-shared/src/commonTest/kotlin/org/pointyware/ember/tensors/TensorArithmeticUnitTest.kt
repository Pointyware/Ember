package org.pointyware.ember.tensors

import org.junit.Test
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.test.assertContentEquals

class TensorArithmeticUnitTest {

    @Test
    fun scalar_addition_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            2, 2
        )
        val scalar = 5.0
        val result = tensor1 + scalar

        val expected = Tensor.from(
            doubleArrayOf(6.0, 7.0, 8.0, 9.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun scalar_subtraction_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(5.0, 6.0, 7.0, 8.0),
            2, 2
        )
        val scalar = 3.0
        val result = tensor1 - scalar

        val expected = Tensor.from(
            doubleArrayOf(2.0, 3.0, 4.0, 5.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(5.0, 6.0, 7.0, 8.0),
            tensor1.data,
            "Operand data should remain unchanged"
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

    @Test
    fun scalar_division_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(2.0, 4.0, 6.0, 8.0),
            2, 2
        )
        val scalar = 2.0
        val result = tensor1 / scalar

        val expected = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(2.0, 4.0, 6.0, 8.0),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun tensor_addition_returns_new_tensor() {
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
    fun tensor_in_place_addition_modifies_first_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            4, 1
        )
        val tensor2 = Tensor.from(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            4, 1
        )
        tensor1 += tensor2

        val expected = Tensor.from(
            doubleArrayOf(2.0, 3.0, 4.0, 5.0),
            4, 1
        )
        assertContentEquals(expected.data, tensor1.data)
        assertContentEquals(
            doubleArrayOf(1.0, 1.0, 1.0, 1.0),
            tensor2.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun tensor_subtraction_returns_new_tensor() {
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
    fun tensor_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            2, 2
        )
        val tensor2 = Tensor.from(
            doubleArrayOf(2.0, 2.0, 2.0, 2.0),
            2, 2
        )
        val result = tensor1 * tensor2

        val expected = Tensor.from(
            doubleArrayOf(2.0, 4.0, 6.0, 8.0),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            doubleArrayOf(2.0, 2.0, 2.0, 2.0),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun matrix_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            4, 1
        )
        val tensor2 = Tensor.from(
            doubleArrayOf(7.0, 8.0, 9.0, 10.0),
            1, 4
        )
        val result = tensor1.matrixMultiply(tensor2)

        val expected = Tensor.from(
            doubleArrayOf(
                7.0, 8.0, 9.0, 10.0,
                14.0, 16.0, 18.0, 20.0,
                21.0, 24.0, 27.0, 30.0,
                28.0, 32.0, 36.0, 40.0
            ),
            4, 4
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            doubleArrayOf(1.0, 2.0, 3.0, 4.0),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            doubleArrayOf(7.0, 8.0, 9.0, 10.0),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }
}
