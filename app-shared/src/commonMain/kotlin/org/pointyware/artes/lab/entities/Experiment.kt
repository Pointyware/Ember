package org.pointyware.artes.lab.entities

/**
 * Expresses a causal relationship between variables.
 *
 * Cause --> Effect
 * Context -Cause-> Effect
 * Initial -Event-> Final
 * Environment -Action-> Outcome
 */
interface Hypothesis<Context, Cause, Effect> {
    val environment: Context
    val action: Cause
    val outcome: Effect
}

/**
 * A series of steps performed on some environment.
 *
 * Necessarily consists of the subject to be tested but also includes any materials required for
 * the experimental trial, as well as all the steps to exercise the hypothesis.
 */
interface Procedure<Environment> {
    val initialState: Any
}

/**
 * A single instance of an experimental test.
 */
interface Trial<Inputs, Outcome> {
    /**
     * The inputs to this single trial.
     */
    val initialState: Inputs

    /**
     * The outcome of this single trial.
     *
     * An implementation could initialize this lazily or throw an IllegalStateException if some
     * required `run()` function isn't called.
     */
    val finalState: Outcome
}

/**
 *
 */
interface Result {

}

/**
 * An experiment involves independent variables and associated dependent variables and attempts to
 * establish or refute a causal relationship between the two, expressed through a hypothesis.
 *
 * Key constituents of an experiment:
 * 1. The hypothesis to be tested.
 * 2. A systematically reproducible procedure used to test the hypothesis.
 * 3. Individual trials which exercise the procedure.
 * 4. Results of the trials that can be subjected to statistical analysis to accept or refute the
 * hypothesis.
 */
interface Experiment<Environment, Cause, Effect, R: Result> {
    /**
     * A testable prediction, formulated in terms of independent variables.
     */
    val hypothesis: Hypothesis<Environment, Cause, Effect>

    /**
     *
     */
    val procedure: Procedure<Environment>

    /**
     *
     */
    fun createTrials(count: Int): List<Trial<Cause, Effect>>

    /**
     *
     */
    val results: List<R>
}
