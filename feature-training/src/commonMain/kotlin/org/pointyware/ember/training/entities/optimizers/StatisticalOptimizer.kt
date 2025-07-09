package org.pointyware.ember.training.entities.optimizers

import org.pointyware.ember.training.entities.Statistics

/**
 * An [Optimizer] that acts on [Statistics] collected during training.
 */
interface StatisticalOptimizer: Statistics, Optimizer {
}
