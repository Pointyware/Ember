package org.pointyware.artes.scratch.layers

import org.junit.Test
import org.pointyware.artes.scratch.activations.ActivationFunction
import org.pointyware.artes.scratch.tensors.Tensor
import kotlin.test.assertEquals

class LinearLayerUnitTest {

    private val errorOpActivation = object : ActivationFunction {
        override fun calculate(input: Tensor): Tensor {
            error("Should not be called in this test")
        }
        override fun derivative(input: Tensor): Tensor {
            error("Should not be called in this test")
        }
    }

    @Test
    fun test_forward_pass() {
        // Given a LinearLayer with specific weights and biases
        val layer = LinearLayer(
            weights = Tensor.zeros(2, 3).apply {
                this[0, 0] = 2.0
                this[0, 1] = 3.0
                this[0, 2] = 5.0

                this[1, 0] = 7.0
                this[1, 1] = 11.0
                this[1, 2] = 13.0
            },
            biases = Tensor.zeros(2, 1).apply {
                this[0, 0] = 17.0
                this[1, 0] = 19.0
            },
            activation = errorOpActivation
        )

        // When the forward method is called with an input tensor
        val input = Tensor.zeros(3, 1).apply {
            this[0] = 23.0
            this[1] = 27.0
            this[2] = 29.0
        }
        val output = layer.forward(input)

        // Then the output tensor should match the expected result
        val expectedOutput = Tensor.zeros(2, 1).apply {
            this[0] = 2.0 * 23.0 +  3.0 * 27.0 +  5.0 * 29.0    + 17.0 // 46 + 81 + 145 + 17
            this[1] = 7.0 * 23.0 + 11.0 * 27.0 + 13.0 * 29.0    + 19.0 // 161 + 297 + 377 + 19
        }
        assertEquals(expectedOutput, output)
    }

    @Test
    fun test_create_layer() {
        // Given input and output sizes
        val inputSize = 3
        val outputSize = 2

        // When a LinearLayer is created
        val layer = LinearLayer.create(
            inputSize = inputSize,
            outputSize = outputSize,
            activation = errorOpActivation
        )

        // Then the layer should have the correct dimensions for weights and biases
        assert(layer.weights.dimensions.contentEquals(intArrayOf(outputSize, inputSize)))
        assert(layer.biases.dimensions.contentEquals(intArrayOf(outputSize, 1)))
    }
}
