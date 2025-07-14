package org.pointyware.ember.entities.networks

import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.regularizers.Regularizer
import org.pointyware.ember.entities.regularizers.RmsNorm
import org.pointyware.ember.entities.tensors.Tensor

class ResidualSequentialNetwork(
    val hidden1: LinearLayer,
    val hidden2: LinearLayer,
    val hidden3: LinearLayer,
    val output: LinearLayer
): SequentialNetwork(listOf(hidden1, hidden2, hidden3, output)) {

    private val regularizer: Regularizer = RmsNorm

    override fun predict(input: Tensor): Tensor {
        val preactivation1 = hidden1.forward(input)
        val activation1 = hidden1.activationFunction.calculate(preactivation1)

        val preactivation2 = hidden2.forward(activation1)
        val activation2 = hidden2.activationFunction.calculate(preactivation2)

        val preactivation3 = hidden3.forward(activation2)
        val activation3 = hidden3.activationFunction.calculate(preactivation3)

        val residualSum = activation1 + activation3
        val normalizedOutput = Tensor(residualSum.dimensions)
        regularizer.predict(residualSum, normalizedOutput)

        val outputPreactivation = output.forward(normalizedOutput)
        val outputActivation = output.activationFunction.calculate(outputPreactivation)
        return outputActivation
    }

    private lateinit var norm: Tensor
    private lateinit var normDerivative: Tensor
    override fun forward(
        input: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>
    ) {
        // z_1 = W_1 * a_0 + B_1
        val preactivation1 = hidden1.forward(input)
        val activation1 = hidden1.activationFunction.calculate(preactivation1)
        activations[0] += activation1
        derivativeActivations[0] += hidden1.activationFunction.derivative(preactivation1)

        // z_2 = W_2 * a_1 + B_2
        val preactivation2 = hidden2.forward(activation1)
        val activation2 = hidden2.activationFunction.calculate(preactivation2)
        activations[1] += activation2
        derivativeActivations[1] += hidden2.activationFunction.derivative(preactivation2)

        // z_3 = W_3 * a_2 + B_3
        val preactivation3 = hidden3.forward(activation2)
        val activation3 = hidden3.activationFunction.calculate(preactivation3)
        activations[2] += activation3
        derivativeActivations[2] += hidden3.activationFunction.derivative(preactivation3)

        // LN = rms(a_1 + a_3)
        val residualSum = activation1 + activation3
        norm = Tensor(residualSum.dimensions)
        normDerivative = Tensor(residualSum.dimensions)
        regularizer.forward(residualSum, norm, normDerivative)

        // z_4 = W_4 * LN + B_4
        val outputPreactivation = output.forward(norm)
        // a_4 = f_4(a_4)
        val outputActivation = output.activationFunction.calculate(outputPreactivation)
        activations[3] += outputActivation
        derivativeActivations[3] += output.activationFunction.derivative(outputPreactivation)
    }

    override fun backward(
        input: Tensor,
        error: Tensor,
        activations: List<Tensor>,
        derivativeActivations: List<Tensor>,
        weightGradients: List<Tensor>,
        biasGradients: List<Tensor>
    ) {
        // d
    }
}
