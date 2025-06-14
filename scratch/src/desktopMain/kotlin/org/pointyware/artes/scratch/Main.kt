package org.pointyware.artes.scratch

import org.pointyware.artes.scratch.activations.ReLU
import org.pointyware.artes.scratch.activations.Sigmoid
import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.loss.MeanSquaredError
import org.pointyware.artes.scratch.networks.SequentialNetwork
import org.pointyware.artes.scratch.optimizers.StochasticGradientDescent
import org.pointyware.artes.scratch.tensors.SimpleTensor
import org.pointyware.artes.scratch.training.SequentialTrainer
import org.pointyware.artes.scratch.training.StudyCase

/**
 * Exercises NN primitives with XOR problem.
 */
fun main(vararg args: String) {
    // Create simple NN with 2 inputs, 1 hidden layer, and 1 output.
    val inputs = listOf(
        SimpleTensor.from(doubleArrayOf(0.0, 0.0)),
        SimpleTensor.from(doubleArrayOf(0.0, 1.0)),
        SimpleTensor.from(doubleArrayOf(1.0, 0.0)),
        SimpleTensor.from(doubleArrayOf(1.0, 1.0))
    )

    val targets = listOf(
        SimpleTensor.from(doubleArrayOf(0.0)),
        SimpleTensor.from(doubleArrayOf(1.0)),
        SimpleTensor.from(doubleArrayOf(1.0)),
        SimpleTensor.from(doubleArrayOf(0.0))
    )

    val network = SequentialNetwork(
        listOf(
            LinearLayer.create(2, 4, ReLU), // 2 in -> 4 out
            LinearLayer.create(4, 1, Sigmoid) // 4 in -> 1 out
        )
    )
    val trainer = SequentialTrainer(
        optimizer = StochasticGradientDescent(0.1),
        lossFunction = MeanSquaredError,
        cases = inputs.zip(targets) { input, target ->
            StudyCase(input, target)
        },
        network = network
    )
    trainer.train(iterations = 1000)

    // Test the trained network
    val testInput = SimpleTensor.from(doubleArrayOf(1.0, 1.0))
    val testOutput = network.predict(testInput)
    val expected = 0.0 // XOR(1, 1) should be 0
    println("\nFinal predictions:")
    println("Input: [%f, %f] -> Output: %.4f, Target: %f".format(testInput[0], testInput[1], testOutput[0], expected))
}
