package org.pointyware.ember.training.entities

import org.pointyware.ember.entities.loss.LossFunction
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.training.entities.optimizers.Optimizer
import org.pointyware.ember.training.entities.optimizers.StatisticalOptimizer

/**
 * A trainer for a [SequentialNetwork] using a list of [Exercise] instances to evaluate
 * against the given [lossFunction], updating the model parameters using the provided [optimizer].
 *
 * The [optimizer] controls how data is sampled from the given [cases] as well as
 * how the [network] parameters are updated during training.
 *
 * **Sampling** - Some optimizers may use all cases for each epoch,
 *   while others may sample a subset of cases.
 * **Iterations** - Some optimizers may update parameters multiple times per epoch,
 *   while others may only update once.
 */
class SequentialTrainer(
    val network: SequentialNetwork,
    val cases: List<Exercise>,
    val lossFunction: LossFunction,
    val optimizer: Optimizer,
    val statistics: Statistics,
    override val updatePeriod: Int = 100
): Trainer {

    constructor(
        network: SequentialNetwork,
        cases: List<Exercise>,
        lossFunction: LossFunction,
        statOptimizer: StatisticalOptimizer,
        updatePeriod: Int = 100
    ): this(network, cases, lossFunction, statOptimizer, statOptimizer, updatePeriod)

    override fun train(iterations: Int) {

        // W^L
        val weightGradients = network.layers.map { Tensor.zeros(*it.weights.dimensions) }
        // b^L
        val biasGradients = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { Tensor.zeros(*it.biases.dimensions) }

        for (epoch in 1..iterations) {
            statistics.onEpochStart(epoch)

            val sampleBatches = optimizer.batch(cases)
            // Create Gradient tensors
            weightGradients.forEach { it.mapEach { 0.0f } }
            biasGradients.forEach { it.mapEach { 0.0f } }

            var aggregateLoss = 0.0

            for (batch in sampleBatches) {
                statistics.onBatchStart(batch)
                val caseCount = batch.size.toFloat()
                batch.forEach { case ->
                    // Zero tensors for activations, derivativeActivations, and errors
                    activations.forEach { it.mapEach { 0.0f } }
                    derivativeActivations.forEach { it.mapEach { 0.0f } }

                    statistics.onSampleStart(case)
                    // Forward Pass - using tensors as gradient receivers
                    network.forward(case.input, activations, derivativeActivations)

                    // Calculate the loss for the current case
                    val output = activations.last()
                    val loss = lossFunction.compute(expected = case.output, actual = output)
                    statistics.onCost(loss)
                    aggregateLoss += loss
                    // $\nabla_{a_L} C$ is the gradient of the loss function with respect to the final layer's output
                    val errorGradient =
                        lossFunction.derivative(expected = case.output, actual = output)

                    // Backward Pass
                    network.backward(
                        case.input,
                        errorGradient,
                        activations,
                        derivativeActivations,
                        weightGradients,
                        biasGradients
                    )
                    statistics.onGradient()

                    statistics.onSampleEnd(case)
                }
                // Average the gradients over all cases
                weightGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }
                biasGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }

                // Adjust parameters for all layers using the optimizer
                network.layers.forEachIndexed { index, layer ->
                    optimizer.update(layer, weightGradients[index], biasGradients[index])
                }
                statistics.onBatchEnd(batch)
            }

            statistics.onEpochEnd(epoch)
        }
    }
}
