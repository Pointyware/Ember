package org.pointyware.ember

import org.pointyware.ember.activations.ReLU
import org.pointyware.ember.activations.Sigmoid
import org.pointyware.ember.layers.LinearLayer
import org.pointyware.ember.loss.MeanSquaredError
import org.pointyware.ember.networks.SequentialNetwork
import org.pointyware.ember.optimizers.StochasticGradientDescent
import org.pointyware.ember.tensors.Tensor
import org.pointyware.ember.tensors.columnVector
import org.pointyware.ember.training.SequentialTrainer
import org.pointyware.ember.training.StudyCase

/**
 * Exercises NN primitives with XOR problem.
 */
fun main(vararg args: String) {
    // Create simple NN with 2 inputs, 1 hidden layer, and 1 output.
    val inputs = listOf(
        columnVector(0.0, 0.0),
        columnVector(0.0, 1.0),
        columnVector(1.0, 0.0),
        columnVector(1.0, 1.0),
    )

    val targets = listOf(
        columnVector(0.0),
        columnVector(1.0),
        columnVector(1.0),
        columnVector(0.0),
    )

    val network = SequentialNetwork.create(
        input = 2,
        3 to Sigmoid,
        1 to Sigmoid,
    )
    val trainer = SequentialTrainer(
        network = network,
        cases = inputs.zip(targets) { input, target ->
            StudyCase(input, target)
        },
        lossFunction = MeanSquaredError,
        optimizer = StochasticGradientDescent(0.10),
        updatePeriod = 10e3.toInt(),
    )
    trainer.train(iterations = 10e4.toInt())

    // Test the trained network
    println("\nFinal predictions:")
    for (left in 0 .. 1) {
        for (right in 0 .. 1) {
            val input = columnVector(left.toDouble(), right.toDouble())
            val output = network.predict(input)
            println("Input: [$left, $right] -> Output: %.4f".format(output[0]))
        }
    }
}
