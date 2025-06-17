package org.pointyware.artes.scratch.layers

import org.pointyware.artes.scratch.activations.ScalarActivationFunction
import org.pointyware.artes.scratch.tensors.Tensor

/**
 * Represents a convolutional layer in a neural network.
 *
 * The [kernel] is expected to be a 3D tensor representing the convolutional filters.
 * The forward method applies the convolution operation to the input tensor.
 * The input tensor is expected to be a 3D tensor with shape (channels, height, width).
 * The output tensor dimensions will depend on the kernel size, stride, and padding.
 */
data class ConvolutionalLayer(
    val kernel: Tensor,
    val stride: Int = 1,
    val padding: Int = 0,
    val activationFunction: ScalarActivationFunction
): Layer {
    override fun forward(input: Tensor): Tensor {
        require(input.dimensions.size == 3) { "Input tensor must be 3D (channels, height, width)" }
        val (channels, height, width) = input.dimensions
        val (kernelChannels, kernelHeight, kernelWidth) = kernel.dimensions
        require(kernel.dimensions[0] == channels) { "Kernel channels must match input channels" }
        val outputWidth = (width + 2 * padding - kernel.dimensions[2]) / stride + 1
        val outputHeight = (height + 2 * padding - kernel.dimensions[1]) / stride + 1
        return Tensor.shape(1, outputHeight, outputWidth).mapEachIndexed { (c, h, w), value ->
            var sum = 0.0
            TODO("Implement convolution operation")
            activationFunction.scalarActivation(sum)
        }
    }
}
