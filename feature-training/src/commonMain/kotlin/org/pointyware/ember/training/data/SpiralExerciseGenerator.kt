package org.pointyware.ember.training.data

import org.pointyware.ember.entities.tensors.columnVector
import org.pointyware.ember.training.entities.Exercise
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Generates a batch of spiral classification exercises.
 */
class SpiralExerciseGenerator(
    val problem: Problem.SpiralClassificationProblem,
    val batchSize: Int = 50,
): ExerciseGenerator {
    override fun generate(): List<Exercise> {
        return List(batchSize) { index ->
            val classId = index % 2
            val phaseOffset = if (classId == 0) {
                0.0
            } else {
                Math.PI
            }
            val t = Random.Default.nextFloat()
            val theta = 2 * Math.PI * t * problem.spiralCount + phaseOffset
            val x = t * cos(theta) * problem.xMagnitude
            val y = t * sin(theta) * problem.yMagnitude
            Exercise(
                input = columnVector(x.toFloat(), y.toFloat()),
                output = columnVector(classId.toFloat()),
            )
        }
    }
}
