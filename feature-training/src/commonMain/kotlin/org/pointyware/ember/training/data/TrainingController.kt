package org.pointyware.ember.training.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.ember.entities.activations.Sigmoid
import org.pointyware.ember.entities.loss.MeanSquaredError
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.entities.optimizers.StochasticGradientDescent
import org.pointyware.ember.entities.tensors.columnVector
import org.pointyware.ember.entities.training.Exercise
import org.pointyware.ember.entities.training.SequentialTrainer
import kotlin.math.min

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
    private val trainingScope: CoroutineScope
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
        _state.update {
            it.copy(
                epochsRemaining = epochs
            )
        }
    }

    private var trainingJob: Job? = null
    override fun start() {
        _state.update {
            if (it.isTraining) {
                it // No change if already training
            } else {
                it.copy(
                    isTraining = true,
                )
            }
        }
        trainingScope.launch { startTraining() }
        trainer.train(iterations = 10e4.toInt())
    }

    private suspend fun startTraining() {
        trainingJob?.cancelAndJoin()
        trainingJob = trainingScope.launch {

            while (state.value.isTraining) {
                val epochsRemaining = _state.value.epochsRemaining
                val trainedEpochs = min(epochsRemaining, trainer.updatePeriod)
                trainer.train(iterations = trainedEpochs)
                val remaining = epochsRemaining - trainedEpochs
                _state.update { currentState ->
                    currentState.copy(
                        isTraining = remaining > 0,
                        epochsRemaining = remaining
                    )
                }
            }
        }
    }

    override fun stop() {
        _state.update {
            it.copy(
                isTraining = false
            )
        }
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
