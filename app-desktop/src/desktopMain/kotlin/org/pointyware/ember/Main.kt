package org.pointyware.ember

import org.pointyware.ember.entities.activations.Sigmoid
import org.pointyware.ember.entities.tensors.columnVector
import org.pointyware.ember.entities.loss.MeanSquaredError
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.optimizers.StochasticGradientDescent
import org.pointyware.ember.entities.training.SequentialTrainer
import org.pointyware.ember.entities.training.Exercise

/**
 * Exercises NN primitives with XOR problem.
 */
fun trainNetwork() {
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
            Exercise(input, target)
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
