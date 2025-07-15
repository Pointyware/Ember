package org.pointyware.ember.training.entities

import kotlinx.coroutines.flow.StateFlow

/**
 * A Trainer defines a workflow for training a model.
 *
 * All trainers are run incrementally, meaning that they can be run multiple times.
 *
 * For each epoch, the trainer has an opportunity to review the training data with or without
 * the student model to select a subset of data to train on.
 *
 *
 * __Epoch__ - A single "step" in a trainer's algorithm. Some optimizers make multiple passes
 * through datasets.
 *
 */
interface Trainer {

    /**
     * Exposes the latest state of training.
     */
    val snapshot: StateFlow<Snapshot>

    /**
     * Train the model for a number of [iterations], a.k.a. epochs.
     *
     * @param iterations The number of epochs to train the model for. Defaults to 1.
     */
    fun train(iterations: Int = 1): Int
}
