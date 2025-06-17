package org.pointyware.artes.scratch.networks

import org.junit.Test
import org.pointyware.artes.scratch.activations.ReLU
import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.tensors.Tensor
import kotlin.test.assertContentEquals

class SequentialNetworkUnitTest {

    @Test
    fun sequential_network_passes_input_through_layers() {
        val layer1 = LinearLayer(
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
            activation = ReLU
        )
        val layer2 = LinearLayer(
            weights = Tensor.zeros(1, 2).apply {
                this[0, 0] = 23.0
                this[0, 1] = 27.0
            },
            biases = Tensor.zeros(1, 1).apply {
                this[0, 0] = 29.0
            },
            activation = ReLU
        )
        val network = SequentialNetwork(listOf(layer1, layer2))
        val input = Tensor.zeros(3, 1).apply {
            this[0] = 2.0
            this[1] = 3.0
            this[2] = 5.0
        }
        val output = network.predict(input)
        // The output should be a tensor of shape (1, 1) since the last layer has 1 output neuron
        assert(output.dimensions.contentEquals(intArrayOf(1, 1))) { "Output shape should be (1, 1), but was ${output.dimensions.joinToString()}" }

        val layer1Output = Tensor.zeros(2, 1).apply {
            this[0, 0] = 2.0 * 2.0 +  3.0 * 3.0 +  5.0 * 5.0 + 17.0
            this[1, 0] = 7.0 * 2.0 + 11.0 * 3.0 + 13.0 * 5.0 + 19.0
        }
        val expected = Tensor.zeros(1, 1).apply {
            this[0, 0] =
                layer1Output[0, 0] * 23.0 + layer1Output[1, 0] * 27.0 + 29.0
        }
        assertContentEquals(expected.data, output.data)
    }
}
