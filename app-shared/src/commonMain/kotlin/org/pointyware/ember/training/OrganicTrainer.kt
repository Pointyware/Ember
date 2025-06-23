package org.pointyware.ember.training

/**
 * An organic trainer attempts to train a model of progressive complexity by growing layers in
 * width and depth, as well as adding functional modules where linearities allow growth without
 * disrupting learned features.
 *
 * The process runs as such:
 * 1. Bootstrap baby network with foundation data.
 * 2. Use network to evaluate training data and determine order of familiarity.
 * 3. Use familiarity to select next training data.
 * 4. During training, grow the network to accommodate plateaus or premature stalls below the
 *   acceptable loss threshold.
 * 5. After training, prune the network to remove unused or redundant layers and connections, and
 *   prevent overfitting.
 *
 * @param pruningPeriod The number of iterations after which the network will be pruned to remove
 *   unused or redundant layers and connections.
 */
class OrganicTrainer(
    val pruningPeriod: Int,
    override val updatePeriod: Int = 100
): Trainer {
    override fun train(iterations: Int) {
        TODO("Not yet implemented")
    }

    override fun selectSamples(): List<StudyCase> {
        TODO("Not yet implemented")
    }

    /**
     * Triggers a pruning operation on the network to remove unused or redundant layers and
     * connections.
     */
    fun prune() {
        TODO("Pruning logic not yet implemented")
    }
}
