package org.pointyware.ember.training

/**
 * A Trainer defines a workflow for training a model.
 *
 * All trainers are run incrementally, meaning that they can be run multiple times.
 *
 * For each epoch, the trainer has an opportunity to review the training data with or without
 * the student model to select a subset of data to train on.
 *
 */
interface Trainer {
    /**
     * The number of iterations between printing updates to the console.
     * -1 means no updates will be printed.
     */
    val updatePeriod: Int

    /**
     * Controls which samples will be used for each epoch.
     */
    fun selectSamples(): List<StudyCase>

    /**
     * Train the model for a number of [iterations], a.k.a. epochs.
     *
     * @param iterations The number of epochs to train the model for. Defaults to 1.
     */
    fun train(iterations: Int = 1)
}
