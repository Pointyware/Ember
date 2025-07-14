package org.pointyware.ember.entities.networks

import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.tensors.Tensor

class ResidualSequentialNetwork(
    val hidden1: LinearLayer,
    val hidden2: LinearLayer,
    val hidden3: LinearLayer,
    val output: LinearLayer
): SequentialNetwork(listOf(hidden1, hidden2, hidden3, output)) {

    private fun layerNorm(input: Tensor): Tensor {
        TODO("Implement layer norm in place?")
    }

    override fun predict(input: Tensor): Tensor {
        val preactivation1 = hidden1.forward(input)
        val activation1 = hidden1.activationFunction.calculate(preactivation1)

        val preactivation2 = hidden2.forward(activation1)
        val activation2 = hidden2.activationFunction.calculate(preactivation2)

        val preactivation3 = hidden3.forward(activation2)
        val activation3 = hidden3.activationFunction.calculate(preactivation3)

        val residualSum = activation1 + activation3
        val normalizedOutput = layerNorm(residualSum)

        val outputPreactivation = output.forward(normalizedOutput)
        val outputActivation = output.activationFunction.calculate(outputPreactivation)
        return outputActivation
    }

    override fun forward(
        input: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>
    ) {
        val preactivation1 = hidden1.forward(input)
        val activation1 = hidden1.activationFunction.calculate(preactivation1)
        activations[0] += activation1
        derivativeActivations[0] += hidden1.activationFunction.derivative(preactivation1)

        val preactivation2 = hidden2.forward(activation1)
        val activation2 = hidden2.activationFunction.calculate(preactivation2)
        activations[1] += activation2
        derivativeActivations[1] += hidden2.activationFunction.derivative(preactivation2)

        val preactivation3 = hidden3.forward(activation2)
        val activation3 = hidden3.activationFunction.calculate(preactivation3)
        activations[2] += activation3
        derivativeActivations[2] += hidden3.activationFunction.derivative(preactivation3)

        val residualSum = activation1 + activation3
        val normalizedOutput = layerNorm(residualSum)
        activations[3] += normalizedOutput
        derivativeActivations[3] += TODO()

        val outputPreactivation = output.forward(normalizedOutput)
        val outputActivation = output.activationFunction.calculate(outputPreactivation)
        activations[4] += outputActivation
        derivativeActivations[4] += output.activationFunction.derivative(outputActivation)
    }

    override fun backward(
        input: Tensor,
        error: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    ) {

    }
}
