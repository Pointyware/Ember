package org.pointyware.ember.entities.networks

import org.junit.Test
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.entities.layers.DenseLayer
import org.pointyware.ember.entities.regularizers.RmsNorm
import org.pointyware.ember.entities.tensors.Tensor.Companion.zeros
import kotlin.test.assertContentEquals

class ResidualSequentialNetworkUnitTest {

    @Test
    fun residual_sequential_network_passes_input_through_layers() {
        val hiddenWidth = 2
        val regularizer = RmsNorm(hiddenWidth)
        val network = ResidualSequentialNetwork(
            listOf(
                DenseLayer(
                    weights = zeros(hiddenWidth, 1).apply {
                        this[0, 0] = 1f
                        this[1, 0] = 2f
                    },
                    biases = zeros(hiddenWidth, 1).apply {
                        this[0, 0] = 3f
                        this[1, 0] = 4f
                    },
                    activationFunction = ReLU
                ),
                DenseLayer(
                    weights = zeros(hiddenWidth, hiddenWidth).apply {
                        this[0, 0] = 5f
                        this[0, 1] = 6f
                        this[1, 0] = 7f
                        this[1, 1] = 8f
                    },
                    biases = zeros(hiddenWidth, 1).apply {
                        this[0, 0] = 9f
                        this[1, 0] = 10f
                    },
                    activationFunction = ReLU
                ),
                DenseLayer(
                    weights = zeros(hiddenWidth, hiddenWidth).apply {
                        this[0, 0] = 11f
                        this[0, 1] = 12f
                        this[1, 0] = 13f
                        this[1, 1] = 14f
                    },
                    biases = zeros(hiddenWidth, 1).apply {
                        this[0, 0] = 15f
                        this[1, 0] = 16f
                    },
                    activationFunction = ReLU
                ),
                DenseLayer(
                    weights = zeros(1, hiddenWidth).apply {
                        this[0, 0] = 17f
                        this[0, 1] = 18f
                    },
                    biases = zeros(1, 1).apply {
                        this[0, 0] = 19f
                    },
                    activationFunction = ReLU
                )
            ),
            residualSplit = 0,
            residualJoin = 2,
            regularizer = regularizer
        )
        val input = zeros(1, 1).apply {
            this[0, 0] = 2f
        }
        val output = network.predict(input)
        // The output should be a tensor of shape (1, 1) since the last layer has 1 output neuron
        assert(output.dimensions.contentEquals(intArrayOf(1, 1))) { "Output shape should be (1, 1), but was ${output.shapeString}" }

        val layer1Output = zeros(2, 1).apply {
            this[0, 0] = 2f * 1f + 3f
            this[1, 0] = 2f * 2f + 4f
        }
        val layer2Output = zeros(2, 1).apply {
            this[0, 0] = layer1Output[0, 0] * 5f + layer1Output[1, 0] * 6f + 9f
            this[1, 0] = layer1Output[0, 0] * 7f + layer1Output[1, 0] * 8f + 10f
        }
        val residualSum = layer1Output + layer2Output
        val regularizerOutput = zeros(2, 1)
        regularizer.predict(residualSum, regularizerOutput)
        val layer3Output = zeros(2, 1).apply {
            this[0, 0] = regularizerOutput[0, 0] * 11f + regularizerOutput[1, 0] * 12f + 15f
            this[1, 0] = regularizerOutput[0, 0] * 13f + regularizerOutput[1, 0] * 14f + 16f
        }
        val expected = zeros(1, 1).apply {
            this[0, 0] =
                layer3Output[0, 0] * 17 + layer3Output[1, 0] * 18 + 19f
        }
        assertContentEquals(expected.data, output.data)
    }
}
