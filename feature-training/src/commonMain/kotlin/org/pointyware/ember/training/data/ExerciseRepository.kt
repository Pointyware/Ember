package org.pointyware.ember.training.data

import org.pointyware.ember.training.entities.Exercise

/**
 * Provides [org.pointyware.ember.training.entities.Exercise] instances
 */
interface ExerciseRepository {
    fun getExercises(problem: Problem): List<Exercise>
}

/**
 *
 */
class ExerciseRepositoryImpl(

): ExerciseRepository {

    override fun getExercises(problem: Problem): List<Exercise> {
        return when (problem) {
            is Problem.XorProblem -> XorExerciseGenerator(problem).generate()
            is Problem.SpiralClassificationProblem -> SpiralExerciseGenerator(problem).generate()
        }
    }
}
