package org.pointyware.ember.training.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.pointyware.ember.entities.activations.Sigmoid
import org.pointyware.ember.entities.loss.MeanSquaredError
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.optimizers.StochasticGradientDescent
import org.pointyware.ember.entities.tensors.columnVector
import org.pointyware.ember.entities.training.Exercise
import org.pointyware.ember.entities.training.SequentialTrainer

/**
 * Models basic training state and provides a handle to the trainer and network.
 */
data class TrainingState(
    val isTraining: Boolean = false,
    val epochsRemaining: Int = 0,
    val trainer: SequentialTrainer
) {
    val networkDepth: Int
        get() = trainer.network.layers.size
}

/**
 * A Training Controller provides an interface to manage training of a model and exposes
 * properties to observe the training state.
 */
interface TrainingController {
    val state: StateFlow<TrainingState>

    /**
     * Sets the number of epochs to train the model. 0 means train indefinitely.
     */
    fun setEpochs(epochs: Int)

    /**
     * Starts the training process.
     */
    fun start()

    /**
     * Stop any ongoing training. The current epoch will be completed before stopping.
     */
    fun stop()

    /**
     * Stops the training process if it is currently running, and resets the training state.
     */
    fun reset()

    /**
     *
     */
    suspend fun test(case: Exercise): Double
}

/**
 *
 */
class TrainingControllerImpl(

): TrainingController {

    val exercises: List<Exercise>
    init {
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
        exercises = inputs.zip(targets) { input, target ->
            Exercise(input, target)
        }
    }

    // Create simple NN with 2 inputs, 1 hidden layer, and 1 output.
    val trainer = SequentialTrainer(
        network = SequentialNetwork.create(
            input = 2,
            3 to Sigmoid,
            1 to Sigmoid,
        ),
        cases = exercises,
        lossFunction = MeanSquaredError,
        optimizer = StochasticGradientDescent(learningRate = 0.10),
        updatePeriod = 10e3.toInt(),
    )
    private val _state = MutableStateFlow(TrainingState(
        isTraining = false,
        epochsRemaining = 0,
        trainer = trainer
    ))
    override val state: StateFlow<TrainingState>
        get() = _state.asStateFlow()

    override fun setEpochs(epochs: Int) {
        TODO("Not yet implemented")
    }

    override fun start() {
        trainer.train(iterations = 10e4.toInt())
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override suspend fun test(case: Exercise): Double {
        // Test the trained network on a single case
        val output = trainer.network.predict(case.input)
        val error = trainer.lossFunction.compute(output, case.output)
        println("Input: ${case.input} -> Error: %.4f Output: $output".format(error))
        return error
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}
