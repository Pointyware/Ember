package org.pointyware.ember.training.entities

/**
 * Represents a single computation in a computation graph.
 * Each implementation should take input keys specific to the computation
 * and output its result to the output key.
 */
abstract class Computation<T: Any>(
    val name: String,
    val outputKey: ComputationKey<T>
) {
    abstract fun compute(context: ComputationContext)
}

/**
 * Computes the current model accuracy against inputs and targets.
 */
class Accuracy(
    key: ComputationKey<Float>
): Computation<Float>("accuracy", key) {
    override fun compute(context: ComputationContext) {
        TODO("Not yet implemented")
        // Run forward pass across inputs and targets to calculate accuracy
    }
}

///**
// *
// */
//class Loss(
//    val lossKey: ComputationKey<Float>,
//    key: ComputationKey<Float>
//): Computation<Float>("loss", key) {
//    override fun compute(context: ComputationContext) {
//        context.put(lossKey, context.get(outputKey))
//    }
//}
