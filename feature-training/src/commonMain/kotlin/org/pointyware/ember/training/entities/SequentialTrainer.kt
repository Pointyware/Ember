package org.pointyware.ember.training.entities

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.ember.entities.loss.LossFunction
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.tensors.TensorPool
import org.pointyware.ember.training.entities.optimizers.SinglePassOptimizer
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
 *
 *   @param acceptableError Once the network reaches this level, it will stop training.
 */
class SequentialTrainer(
    val network: SequentialNetwork,
    val cases: List<Exercise>,
    val lossFunction: LossFunction,
    val optimizer: SinglePassOptimizer,
    val statistics: Statistics,
    private val acceptableError: Double = .001
): Trainer {

    private val errorMeasure = Measurement.Loss
    private var done = false

    constructor(
        network: SequentialNetwork,
        cases: List<Exercise>,
        lossFunction: LossFunction,
        optimizer: StatisticalOptimizer,
    ): this(network, cases, lossFunction, optimizer, optimizer)

    private var epochStatistics: EpochStatistics? = statistics as? EpochStatistics
    private var batchStatistics: BatchStatistics? = statistics as? BatchStatistics
    private var sampleStatistics: SampleStatistics? = statistics as? SampleStatistics
    // TODO: create forward and backward graphs from network structure to collect each layer stage
    private var layerStatistics: LayerStatistics? = statistics as? LayerStatistics

    private val _snapshot = MutableStateFlow(Snapshot.empty)
    override val snapshot: StateFlow<Snapshot>
        get() = _snapshot.asStateFlow()

    private var tensorPool = TensorPool()
    private var epoch: Int = 0
    override fun train(iterations: Int): Int {
        if (done) return 0

        // W^L
        val weightGradients = network.layers.map { tensorPool.getObject(it.weights.dimensions) }
        // b^L
        val biasGradients = network.layers.map { tensorPool.getObject(it.biases.dimensions) }
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { tensorPool.getObject(it.biases.dimensions) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { tensorPool.getObject(it.biases.dimensions) }

        var latestSnapshot = snapshot.value
        repeat(iterations) { index ->
            epoch++
            epochStatistics?.onEpochStart(epoch)

            val sampleBatches = optimizer.batch(cases)
            // Create Gradient tensors
            weightGradients.forEach { it.zero() }
            biasGradients.forEach { it.zero() }

            var aggregateLoss = 0.0

            for (batch in sampleBatches) {
                batchStatistics?.onBatchStart(batch)
                val caseCount = batch.size.toFloat()
                batch.forEach { case ->
                    // Zero tensors for activations, derivativeActivations, and errors
                    activations.forEach { it.zero() }
                    derivativeActivations.forEach { it.zero() }

                    sampleStatistics?.onSampleStart(case)
                    // Forward Pass - using tensors as gradient receivers
                    network.forward(case.input, activations, derivativeActivations)

                    // Calculate the loss for the current case
                    val output = activations.last()
                    val loss = lossFunction.compute(expected = case.output, actual = output)
                    sampleStatistics?.onCost(loss)
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
                    sampleStatistics?.onGradient()

                    sampleStatistics?.onSampleEnd(case)
                }
                // Average the gradients over all cases
                weightGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }
                biasGradients.forEach { gradient -> gradient.mapEach { it / caseCount } }

                // Adjust parameters for all layers using the optimizer
                network.layers.forEachIndexed { index, layer ->
                    optimizer.update(layer, weightGradients[index], biasGradients[index])
                }
                batchStatistics?.onBatchEnd(batch)
            }
            epochStatistics?.onEpochEnd(epoch)

            latestSnapshot = statistics.createSnapshot()
            _snapshot.value = latestSnapshot

            if (latestSnapshot.measurements[errorMeasure]!!.last().second < acceptableError) {
                done = true
                return index + 1
            }
        }

        return iterations // TODO: allow halting training early on convergence
    }
}
