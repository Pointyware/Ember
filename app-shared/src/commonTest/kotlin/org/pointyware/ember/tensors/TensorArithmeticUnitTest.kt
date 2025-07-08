package org.pointyware.ember.tensors

import org.junit.Test
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.test.assertContentEquals

class TensorArithmeticUnitTest {

    @Test
    fun scalar_addition_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            2, 2
        )
        val scalar = 5.0f
        val result = tensor1 + scalar

        val expected = Tensor.from(
            floatArrayOf(6.0f, 7.0f, 8.0f, 9.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun scalar_subtraction_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(5.0f, 6.0f, 7.0f, 8.0f),
            2, 2
        )
        val scalar = 3.0f
        val result = tensor1 - scalar

        val expected = Tensor.from(
            floatArrayOf(2.0f, 3.0f, 4.0f, 5.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(5.0f, 6.0f, 7.0f, 8.0f),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun scalar_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            2, 2
        )
        val scalar = 2.0f
        val result = tensor1 * scalar

        val expected = Tensor.from(
            floatArrayOf(2.0f, 4.0f, 6.0f, 8.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun scalar_division_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(2.0f, 4.0f, 6.0f, 8.0f),
            2, 2
        )
        val scalar = 2.0f
        val result = tensor1 / scalar

        val expected = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(2.0f, 4.0f, 6.0f, 8.0f),
            tensor1.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun tensor_addition_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            2, 2
        )
        val tensor2 = Tensor.from(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            2, 2
        )
        val result = tensor1 + tensor2

        val expected = Tensor.from(
            floatArrayOf(2.0f, 3.0f, 4.0f, 5.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun tensor_in_place_addition_modifies_first_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            4, 1
        )
        val tensor2 = Tensor.from(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            4, 1
        )
        tensor1 += tensor2

        val expected = Tensor.from(
            floatArrayOf(2.0f, 3.0f, 4.0f, 5.0f),
            4, 1
        )
        assertContentEquals(expected.data, tensor1.data)
        assertContentEquals(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            tensor2.data,
            "Operand data should remain unchanged"
        )
    }

    @Test
    fun tensor_subtraction_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(5.0f, 6.0f, 7.0f, 8.0f),
            4, 1
        )
        val tensor2 = Tensor.from(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            4, 1
        )
        val result = tensor1 - tensor2

        val expected = Tensor.from(
            floatArrayOf(4.0f, 5.0f, 6.0f, 7.0f),
            4, 1
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(5.0f, 6.0f, 7.0f, 8.0f),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun tensor_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            2, 2
        )
        val tensor2 = Tensor.from(
            floatArrayOf(2.0f, 2.0f, 2.0f, 2.0f),
            2, 2
        )
        val result = tensor1 * tensor2

        val expected = Tensor.from(
            floatArrayOf(2.0f, 4.0f, 6.0f, 8.0f),
            2, 2
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            floatArrayOf(2.0f, 2.0f, 2.0f, 2.0f),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }

    @Test
    fun matrix_multiplication_returns_new_tensor() {
        val tensor1 = Tensor.from(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            4, 1
        )
        val tensor2 = Tensor.from(
            floatArrayOf(7.0f, 8.0f, 9.0f, 10.0f),
            1, 4
        )
        val result = tensor1.matrixMultiply(tensor2)

        val expected = Tensor.from(
            floatArrayOf(
                7.0f, 8.0f, 9.0f, 10.0f,
                14.0f, 16.0f, 18.0f, 20.0f,
                21.0f, 24.0f, 27.0f, 30.0f,
                28.0f, 32.0f, 36.0f, 40.0f
            ),
            4, 4
        )
        assertContentEquals(expected.data, result.data)
        assertContentEquals(
            floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
            tensor1.data,
            "Operand 1 data should remain unchanged"
        )
        assertContentEquals(
            floatArrayOf(7.0f, 8.0f, 9.0f, 10.0f),
            tensor2.data,
            "Operand 2 data should remain unchanged"
        )
    }
}
