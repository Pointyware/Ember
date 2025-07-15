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

    private val jacobianSize = hidden3.biases.dimensions[0]
    private val regularizer: Regularizer = RmsNorm(jacobianSize)

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
        norm = Tensor.zeros(jacobianSize, 1)
        normDerivative = Tensor.zeros(jacobianSize, jacobianSize)
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
        // when calculating normDerivative, it is a Jacobian matrix (dy/dx)^T. To find
        //   input layer gradient from normalizing layer, multiply error attributed to
        //   containing layer into transpose of jacobian to find error attributed
        //   to normalizing layer, which can then be treated the same as other
        //   errors propagated from simple layers.

        // dCda_4 = error
        val dCda_4 = error
        // dCdz_4 = dCda_4 * da_4dz_4
//        val dCdz_4 = dCda_4 * derivativeActivations[3]
//        // dCdw_4 = dCdz_4 * dz_4dw_4
//        val dCdw_4 = dCdz_4 * activations[3]
//        // dCdb_4 = dCdz_4 * dz_4db_4
//        val dCdb_4 = dCdz_4
        val normError = Tensor(hidden3.biases.dimensions)
        output.backward(
            dCda_4,
            activations[2], derivativeActivations[2],
            weightGradients[3], biasGradients[3],
            normError
        )

        // dCdN = dCdz_4 * dz_4dN
//        val dCdN = weights[3].transpose().matrixMultiply(dCdz_4)
//        // dCdg = dCdN * dNdg
//        val dCdg = dCdn * dNdg // TODO: calculate derivative of normal with respect to gain
        val hidden3Error = Tensor(hidden2.biases.dimensions)
        regularizer.backward(
            normError,
            norm,
            hidden3Error
        )

        // dCda_3 = dCdN * dNda_3
//        val dCda_3 = normDerivative.transpose().matrixMultiply(dCdN)
        // dCdz_3 = dCda_3 * da_3dz_3
//        val dCdz_3 = dCda_3 * derivativeActivations[2]
//        // dCdw_3 = dCdz_3 * dz_3dw_3
//        val dCdw_3 = dCdz_3 * activations[2]
//        // dCdb_3 = dCdz_3 * dz_3db_3
//        val dCdb_3 = dCdz_3
        val hidden2Error = Tensor(hidden1.biases.dimensions)
        hidden3.backward(
            hidden3Error,
            activations[1], derivativeActivations[1],
            weightGradients[2], biasGradients[2],
            hidden2Error
        )

        // dCda_2 = dCdz_3 * dz_3da_2
//        val dCda_2 = weights[2].transpose().matrixMultiply(dCdz_3)
        // dCdz_2 = dCda_2 * da_2dz_2
//        val dCdz_2 = dCda_2 * derivativeActivations[1]
//        // dCdw_2 = dCdz_2 * dz_2dw_2
//        val dCdw_2 = dCdz_2 * activations[1]
//        // dCdb_2 = dCdz_2 * dz_2db_2
//        val dCdb_2 = dCdz_2
        val hidden1Error = Tensor(hidden1.biases.dimensions)
        hidden2.backward(
            hidden2Error,
            activations[0], derivativeActivations[0],
            weightGradients[1], biasGradients[1],
            hidden1Error
        )
        hidden1Error += hidden2Error

        // dCda_1 = dCdz_2 * dz_2da_1
//        val dCda_1 = weights[1].transpose().matrixMultiply(dCdz_2)
        // dCdz_1 = dCda_1 * da_1dz_1
//        val dCdz_1 = dCda_1 * derivativeActivations[0]
//        // dCdw_1 = dCdz_1 * dz_1dw_1
//        val dCdw_1 = dCdz_1 * input
//        // dCdb_1 = dCdz_1 * dz_1db_1
//        val dCdb_1 = dCdz_1
        val inputError = Tensor.zeros(hidden1.weights.dimensions[0])
        hidden1.backward(
            hidden1Error,
            input, Tensor.ones(input.dimensions),
            weightGradients[0], biasGradients[0],
            inputError
        )
    }
}
