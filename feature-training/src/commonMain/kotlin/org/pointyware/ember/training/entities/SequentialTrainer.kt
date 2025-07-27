package org.pointyware.ember.training.entities

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.ember.entities.loss.LossFunction
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.tensors.Tensor
import org.pointyware.ember.entities.tensors.TensorPool
import org.pointyware.ember.training.entities.optimizers.MultiPassOptimizer
import org.pointyware.ember.training.entities.optimizers.Optimizer
import org.pointyware.ember.training.entities.optimizers.SinglePassOptimizer
import org.pointyware.ember.training.entities.optimizers.StatisticalOptimizer
import org.pointyware.ember.training.interactors.lossKey

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
    val optimizer: Optimizer,
    val statistics: Statistics,
    private val acceptableError: Double = .001
): Trainer {

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

    private val singlePassOptimizer: SinglePassOptimizer? = optimizer as? SinglePassOptimizer
    private val multiPassOptimizer: MultiPassOptimizer? = optimizer as? MultiPassOptimizer
    init {
        require(singlePassOptimizer != null || multiPassOptimizer != null) {
            "The given optimizer ($optimizer) must implement SinglePassOptimizer or MultiPassOptimizer."
        }
    }

    private val _snapshot = MutableStateFlow(Snapshot.empty)
    override val snapshot: StateFlow<Snapshot>
        get() = _snapshot.asStateFlow()

    private var tensorPool = TensorPool()
    private var epoch: Int = 0
    override fun train(iterations: Int): Int {
        if (done) return 0

        val computationContext = ComputationContext()

        val weightGradientsKey = 0L.key<List<Tensor>>()
        val biasGradientsKey = 1L.key<List<Tensor>>()
        val activationsKey = 2L.key<List<Tensor>>()
        val derivativeActivationsKey = 3L.key<List<Tensor>>()

        // W^L
        val weightGradients = network.layers.map { tensorPool.getObject(it.weights.dimensions.toList()) }
        // b^L
        val biasGradients = network.layers.map { tensorPool.getObject(it.biases.dimensions.toList()) }
        // x = z^0
        // z^L = W^L \dot a^(L-1) + b^L
        // f(z^L) = a^L
        val activations = network.layers.map { tensorPool.getObject(it.biases.dimensions.toList()) }
        // f'(z^L) = a'^L
        val derivativeActivations = network.layers.map { tensorPool.getObject(it.biases.dimensions.toList()) }

        computationContext.put(weightGradientsKey, weightGradients)
        computationContext.put(biasGradientsKey, biasGradients)
        computationContext.put(activationsKey, activations)
        computationContext.put(derivativeActivationsKey, derivativeActivations)

        var latestSnapshot: Snapshot
        repeat(iterations) { index ->
            epoch++
            epochStatistics?.onEpochStart(epoch, computationContext)
            var step = 0
            do { // Sample Passes
                step++
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

                    // Adjust parameters for all layers using the optimizers
                    singlePassOptimizer?.let {
                        network.layers.forEachIndexed { index, layer ->
                            it.update(
                                epoch,
                                layer,
                                weightGradients[index],
                                biasGradients[index]
                            )
                        }
                    }
                    multiPassOptimizer?.let {
                        network.layers.forEachIndexed { index, layer ->
                            it.update(
                                step,
                                epoch,
                                layer,
                                weightGradients[index],
                                biasGradients[index]
                            )
                        }
                    }
                    network.layers.forEachIndexed { index, layer ->
                        singlePassOptimizer?.update(
                            epoch,
                            layer,
                            weightGradients[index],
                            biasGradients[index]
                        )
                    }
                    batchStatistics?.onBatchEnd(batch)
                }
            } while (multiPassOptimizer?.passAgain() == true)
            epochStatistics?.onEpochEnd(epoch, computationContext)

            latestSnapshot = statistics.createSnapshot()
            _snapshot.value = latestSnapshot

            // TODO: ensure error is put in context; move stopping condition to optimizer?
            val error = computationContext.get(lossKey)
            if (error < acceptableError) {
                done = true
                return index + 1
            }
        }

        weightGradients.forEach { tensorPool.returnObject(it) }
        biasGradients.forEach { tensorPool.returnObject(it) }
        activations.forEach { tensorPool.returnObject(it) }
        derivativeActivations.forEach { tensorPool.returnObject(it) }

        return iterations // TODO: allow halting training early on convergence
    }
}
