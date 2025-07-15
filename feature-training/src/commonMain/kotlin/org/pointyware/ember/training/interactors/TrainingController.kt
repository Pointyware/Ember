package org.pointyware.ember.training.interactors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pointyware.ember.entities.activations.Sigmoid
import org.pointyware.ember.entities.layers.LinearLayer
import org.pointyware.ember.entities.loss.MeanSquaredError
import org.pointyware.ember.entities.networks.ResidualSequentialNetwork
import org.pointyware.ember.entities.networks.SequentialNetwork
import org.pointyware.ember.training.data.ExerciseRepository
import org.pointyware.ember.training.data.Problem
import org.pointyware.ember.training.data.SpiralExerciseGenerator
import org.pointyware.ember.training.entities.Exercise
import org.pointyware.ember.training.entities.SequentialStatistics
import org.pointyware.ember.training.entities.SequentialTrainer
import org.pointyware.ember.training.entities.Snapshot
import org.pointyware.ember.training.entities.optimizers.GradientDescent
import kotlin.math.min

/**
 * Models basic training state and provides a handle to the trainer and network.
 */
data class TrainingState(
    val isTraining: Boolean = false,
    val epochsRemaining: Int = 0,
    val networks: List<NetworkTrainingState> = emptyList(),
)

data class NetworkTrainingState(
    val elapsedEpochs: Int = 0,
    val trainer: SequentialTrainer,
    val snapshot: Snapshot
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
    private val exerciseRepository: ExerciseRepository,
    private val trainingScope: CoroutineScope
): TrainingController {

    private var exercises: List<Exercise> = exerciseRepository.getExercises(Problem.XorProblem(0f, 1))

    private val spiralCases = SpiralExerciseGenerator(Problem.SpiralClassificationProblem(2f, 2f)).generate()

    private val hiddenWidth = 7
    // Create simple NN with 2 inputs, 1 hidden layer, and 1 output.
    private val _state = MutableStateFlow(TrainingState(
        isTraining = false,
        epochsRemaining = 0,
        networks = listOf(
            NetworkTrainingState(
                trainer = SequentialTrainer(
                    network = SequentialNetwork(listOf(
                        LinearLayer.create(2, hiddenWidth, Sigmoid),
                        LinearLayer.create(hiddenWidth, hiddenWidth, Sigmoid),
                        LinearLayer.create(hiddenWidth, hiddenWidth, Sigmoid),
                        LinearLayer.create(hiddenWidth, 1, Sigmoid)
                    )),
                    cases = spiralCases,
                    lossFunction = MeanSquaredError,
                    optimizer = GradientDescent(learningRate = 0.1f),
                    statistics = SequentialStatistics()
                ),
                snapshot = Snapshot.empty
            ),

            NetworkTrainingState(
                trainer = SequentialTrainer(
                    network = ResidualSequentialNetwork(
                        hidden1 = LinearLayer.create(2, hiddenWidth, Sigmoid),
                        hidden2 = LinearLayer.create(hiddenWidth, hiddenWidth, Sigmoid),
                        hidden3 = LinearLayer.create(hiddenWidth, hiddenWidth, Sigmoid),
                        output = LinearLayer.create(hiddenWidth, 1, Sigmoid)
                    ),
                    cases = spiralCases,
                    lossFunction = MeanSquaredError,
                    optimizer = GradientDescent(learningRate = 0.1f),
                    statistics = SequentialStatistics()
                ),
                snapshot = Snapshot.empty
            )
        )
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
    }

    private val trainingStep = 100
    private suspend fun startTraining() {
        trainingJob?.cancelAndJoin()
        trainingJob = trainingScope.launch {

            while (state.value.isTraining) {
                val epochsBeforeTraining = state.value.epochsRemaining
                val epochsToTrain = min(epochsBeforeTraining, trainingStep)

                val networkStatesPostTraining = state.value.networks.map {
                    val trained = it.trainer.train(iterations = epochsToTrain)
                    it.copy(
                        elapsedEpochs = it.elapsedEpochs + trained,
                        snapshot = it.trainer.statistics.createSnapshot()
                    )
                }
                val remaining = epochsBeforeTraining - epochsToTrain

                _state.update { currentState ->
                    if (remaining <= 0) {
                        currentState.copy(
                            isTraining = false,
                            epochsRemaining = 0,
                            networks = networkStatesPostTraining
                        )
                    } else {
                        // Update the state to reflect remaining epochs
                        currentState.copy(
                            epochsRemaining = remaining,
                            networks = networkStatesPostTraining
                        )
                    }
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
//        // Test the trained network on a single case
//        val output = trainer.network.predict(case.input)
//        val error = trainer.lossFunction.compute(output, case.output)
//        println("Input: ${case.input} -> Error: %.4f Output: $output".format(error))
//        return error
        TODO("Ensure that trainers are working on the same problem set")
    }

    override fun reset() {
        _state.update {
            it.copy(
                isTraining = false,
                epochsRemaining = 0,
            )
        }
    }
}
