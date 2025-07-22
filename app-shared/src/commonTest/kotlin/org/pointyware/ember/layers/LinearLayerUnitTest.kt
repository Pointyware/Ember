package org.pointyware.ember.layers

import org.junit.Test
import org.pointyware.ember.entities.activations.ActivationFunction
import org.pointyware.ember.entities.layers.DenseLayer
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.test.assertEquals

class LinearLayerUnitTest {

    private val errorOpActivation = object : ActivationFunction {
        override val parameterCount: Int
            get() = error("Should not be called in this test")

        override fun calculate(input: Tensor): Tensor {
            error("Should not be called in this test")
        }
        override fun derivative(input: Tensor): Tensor {
            error("Should not be called in this test")
        }
        override fun calculate(input: Tensor, activation: Tensor, derivativeActivation: Tensor) {
            error("Should not be called in this test")
        }
    }

    @Test
    fun test_forward_pass() {
        // Given a LinearLayer with specific weights and biases
        val layer = DenseLayer(
            weights = Tensor.zeros(2, 3).apply {
                this[0, 0] = 2.0f
                this[0, 1] = 3.0f
                this[0, 2] = 5.0f

                this[1, 0] = 7.0f
                this[1, 1] = 11.0f
                this[1, 2] = 13.0f
            },
            biases = Tensor.zeros(2, 1).apply {
                this[0, 0] = 17.0f
                this[1, 0] = 19.0f
            },
            activationFunction = errorOpActivation
        )

        // When the forward method is called with an input tensor
        val input = Tensor.zeros(3, 1).apply {
            this[0] = 23.0f
            this[1] = 27.0f
            this[2] = 29.0f
        }
        val output = layer.forward(input)

        // Then the output tensor should match the expected result
        val expectedOutput = Tensor.zeros(2, 1).apply {
            this[0] = 2.0f * 23.0f +  3.0f * 27.0f +  5.0f * 29.0f    + 17.0f // 46 + 81 + 145 + 17
            this[1] = 7.0f * 23.0f + 11.0f * 27.0f + 13.0f * 29.0f    + 19.0f // 161 + 297 + 377 + 19
        }
        assertEquals(expectedOutput, output)
    }

    @Test
    fun test_create_layer() {
        // Given input and output sizes
        val inputSize = 3
        val outputSize = 2

        // When a LinearLayer is created
        val layer = DenseLayer.create(
            inputSize = inputSize,
            outputSize = outputSize,
            activation = errorOpActivation
        )

        // Then the layer should have the correct dimensions for weights and biases
        assert(layer.weights.dimensions.contentEquals(intArrayOf(outputSize, inputSize)))
        assert(layer.biases.dimensions.contentEquals(intArrayOf(outputSize, 1)))
    }
}
