package org.pointyware.artes.core.entities

/**
 * An external element that allows an entity to interact in a new way with its environment. These
 * can facilitate existing skills or augment the entity with entirely new abilities.
 *
 * So far these have been explored only in LLMs (as far as I'm aware), but I predict other tools
 * could be triggered by one or more other systems. A simple example could be a blood glucose meter
 * that automatically doses insulin to regulate the metabolism of someone with diabetes. The tool
 * may have its own "intelligence" - like traditional digital chips - that dictates its outputs.
 *
 * Example of a search tool that could be used by a chat AI:
 * ```kotlin
 * interface ChatConsole { fun append(input: String) /* etc. */ }
 * data class Query(val site: String, val subject: String)
 * interface SearchService { fun search(query: Query): String }
 * class Search (
 *   private val service: SearchService
 * ): Tool<ChatConsole, String, Query> {
 *   override val description = "Empowers an agent to search a variety of "
 *   override val examples = listOf(
 *     Tool.Example(
 *       context = "You want to gather popular information about cats.",
 *       action = Query("google", "cats")
 *     ),
 *     Tool.Example(
 *       context = "You want to gather focused, encyclopedic knowledge about cats.",
 *       action = Query("wikipedia", "cats")
 *     )
 *   )
 *   override fun use(environment: ChatConsole, action: Query) {
 *     environment.append(service.search(action))
 *   }
 * }
 * ```
 *
 * This example ignores the mechanism of activation. In this case, it would probably be a
 * `ToolUseChatConsole` that watches chat output to detect when the AI is attempting to use a tool,
 * which would require a command format associated with the Query to be used in examples and
 * detection.
 *
 * Multimodal AIs would need to have tools bound to the appropriate output through other mechanisms,
 * such as an AI driving an arte's body through motor cortex analogs. The tool here would be
 * circuitry, driving servos/motors, triggered by generalized motion signals output by the AI.
 * Specialized AIs that drive dedicated bodies would likely be more performant if the motor outputs
 * were trained directly. This application of "tools" to drive bodies would be useful only for AIs
 * that could dynamically adopt bodies of different forms.
 *
 * context = PhysicalEmbedding("Increase Elevation"), action = DriveRotors(80.0f)
 */
interface Tool<E, C, A> {

    data class Example<C, A>(
        val context: C,
        val action: A
    )

    /**
     * Human readable description of the tool.
     */
    val description: String

    /**
     * Describes how to use the tool for an agent that should
     * produce an action of type [A] when in context of type [C].
     */
    val examples: List<Example<C, A>>

    /**
     * Exercises the given [action] for the given [environment].
     * @param environment Describes the state that can be manipulated.
     * @param action The action an agent is trying to execute on the given environment.
     */
    fun use(environment: E, action: A)
}
