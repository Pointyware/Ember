package org.pointyware.artes.scratch

import org.pointyware.artes.scratch.activations.ReLU
import org.pointyware.artes.scratch.activations.Sigmoid
import org.pointyware.artes.scratch.layers.LinearLayer
import org.pointyware.artes.scratch.loss.MeanSquaredError
import org.pointyware.artes.scratch.networks.SequentialNetwork
import org.pointyware.artes.scratch.optimizers.StochasticGradientDescent
import org.pointyware.artes.scratch.tensors.Tensor
import org.pointyware.artes.scratch.tensors.columnVector
import org.pointyware.artes.scratch.training.SequentialTrainer
import org.pointyware.artes.scratch.training.StudyCase

/**
 * Exercises NN primitives with XOR problem.
 */
fun main(vararg args: String) {
    // Create simple NN with 2 inputs, 1 hidden layer, and 1 output.
    val inputs = List<Tensor>(100) {
        columnVector(Math.random(), Math.random())
    }

    val targets = inputs.map { case ->
        // XOR logic: 1 if inputs are different, 0 if they are the same
        val left = case[0] > 0.5
        val right = case[1] > 0.5
        columnVector(if (left != right) 1.0 else 0.0)
    }

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
