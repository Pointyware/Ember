package org.pointyware.ember.training.data

import org.pointyware.ember.training.entities.Exercise

/**
 * Generates lists of exercises for use in training against specific problems.
 */
interface ExerciseGenerator {
    fun generate(): List<Exercise>
}
