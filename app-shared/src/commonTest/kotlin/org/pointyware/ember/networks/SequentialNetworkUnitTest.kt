package org.pointyware.ember.networks

import org.junit.Test
import org.pointyware.ember.entities.activations.ReLU
import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.tensors.Tensor
import kotlin.test.assertContentEquals

class SequentialNetworkUnitTest {

    @Test
    fun sequential_network_passes_input_through_layers() {
        val layer1 = LinearLayer(
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
            activation = ReLU
        )
        val layer2 = LinearLayer(
            weights = Tensor.zeros(1, 2).apply {
                this[0, 0] = 23.0f
                this[0, 1] = 27.0f
            },
            biases = Tensor.zeros(1, 1).apply {
                this[0, 0] = 29.0f
            },
            activation = ReLU
        )
        val network = SequentialNetwork(listOf(layer1, layer2))
        val input = Tensor.zeros(3, 1).apply {
            this[0] = 2.0f
            this[1] = 3.0f
            this[2] = 5.0f
        }
        val output = network.predict(input)
        // The output should be a tensor of shape (1, 1) since the last layer has 1 output neuron
        assert(output.dimensions.contentEquals(intArrayOf(1, 1))) { "Output shape should be (1, 1), but was ${output.shapeString}" }

        val layer1Output = Tensor.zeros(2, 1).apply {
            this[0, 0] = 2.0f * 2.0f +  3.0f * 3.0f +  5.0f * 5.0f + 17.0f
            this[1, 0] = 7.0f * 2.0f + 11.0f * 3.0f + 13.0f * 5.0f + 19.0f
        }
        val expected = Tensor.zeros(1, 1).apply {
            this[0, 0] =
                layer1Output[0, 0] * 23.0f + layer1Output[1, 0] * 27.0f + 29.0f
        }
        assertContentEquals(expected.data, output.data)
    }
}
