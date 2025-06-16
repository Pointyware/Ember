package org.pointyware.artes.scratch.training

/**
 * A Trainer defines a workflow for training a model.
 *
 * All trainers are run incrementally, meaning that they can be run multiple times.
 *
 */
interface Trainer {
    /**
     * The number of iterations between printing updates to the console.
     * -1 means no updates will be printed.
     */
    val updatePeriod: Int

    /**
     * Train the model for a number of iterations.
     *
     * @param iterations The number of iterations to train the model for. Defaults to 1.
     */
    fun train(iterations: Int = 1)
}
