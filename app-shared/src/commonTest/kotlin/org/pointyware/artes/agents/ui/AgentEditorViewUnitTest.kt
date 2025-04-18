package org.pointyware.artes.agents.ui

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.viewmodels.AgentEditorUiState
import org.pointyware.artes.viewmodels.HostConfigUiState
import org.pointyware.artes.viewmodels.ModelUiState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class AgentEditorViewUnitTest {

    private val SemanticsNodeInteractionsProvider.agentNameField: SemanticsNodeInteraction
        get() = onNodeWithText("Agent Name")
    private val SemanticsNodeInteractionsProvider.hostSelector: SemanticsNodeInteraction
        get() = onAllNodesWithText("Select an Option").onFirst()
    private val SemanticsNodeInteractionsProvider.modelSelector: SemanticsNodeInteraction
        get() = onAllNodesWithText("Select an Option").onLast()
    private val SemanticsNodeInteractionsProvider.submitButton: SemanticsNodeInteraction
        get() = onNodeWithText("Submit")


    private fun SemanticsNodeInteraction.assertEditableTextEquals(
        expected: String
    ): SemanticsNodeInteraction = assert(editableTextEquals(expected))

    private fun editableTextEquals(
        expected: String
    ): SemanticsMatcher {
        val propertyName = SemanticsProperties.EditableText.name
        return SemanticsMatcher(
            "$propertyName = [$expected]"
        ) { node ->
            node.config.getOrNull(SemanticsProperties.EditableText)?.text == expected
        }
    }

    private var actualName: String? = null
    private var actualHost: Int? = null
    private var actualModel: Long? = null
    private var actualInstructions: String? = null

    @BeforeTest
    fun setUp() {
        actualName = null
        actualHost = null
        actualModel = null
        actualInstructions = null
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun model_name_can_be_changed() = runComposeUiTest {
        /*
        Given: An empty new agent view state,
        And: A new agent view
         */
        val state = AgentEditorUiState(
            agentName = "",
            hosts = emptyList(),
            selectedHost = null,
            hostModels = emptyList(),
            selectedModel = null,
            instructions = ""
        )

        /*
        When: A new agent view is displayed with given state
         */
        setContent {
            AgentEditorView(
                state = state,
                onSelectHost = {},
                onSubmit = { _, _, _ -> }
            )
        }

        /*
        Then: The model name field is empty
        And: the host selector is disabled
        And: the model selector is disabled
         */
        agentNameField.assertEditableTextEquals("")
        hostSelector.assertIsNotEnabled()
        modelSelector.assertIsNotEnabled()

        /*
        When: The user enters a model name
         */
        agentNameField.performTextInput("agent1")

        /*
        Then: The model name is updated
         */
        onNodeWithText("agent1")
            .assertExists()
    }

    @Test
    fun model_host_can_be_selected() = runComposeUiTest {
        /*
        Given: a new agent view state with a non-empty name, and two hosts
        And: no host is selected
         */
        val state = AgentEditorUiState(
            agentName = "Some agent",
            hosts = listOf(
                HostConfigUiState(0L, "host1", OpenAi),
                HostConfigUiState(1L, "host2", OpenAi),
            ),
            selectedHost = null,
            hostModels = emptyList(),
            selectedModel = null,
            instructions = ""
        )

        /*
        When: A new agent view is displayed with given state
         */
        setContent {
            AgentEditorView(
                state = state,
                onSelectHost = { actualHost = it },
                onSubmit = { _, _, _ ->
                    throw Exception("onSubmit should not be called")
                }
            )
        }

        /*
        Then: The agent name field displays the given name
        And: The host selector is enabled
        And: the model selector is disabled
         */
        agentNameField.assertEditableTextEquals("Some agent")
        hostSelector.assertIsEnabled()
        modelSelector.assertIsNotEnabled()

        /*
        When: The user opens the host selector
        And: selects a host
         */
        hostSelector.performClick()
        onNodeWithText("host1").performClick()

        /*
        Then: The host selected callback is invoked with 0
         */
        assertEquals(0, actualHost)
    }

    @Test
    fun agent_model_can_be_selected() = runComposeUiTest {
        /*
        Given: a new agent view state with a non-empty name, and two hosts
        And: the first host is selected
        And: two models are available
        And: no model is selected
         */
        val state = AgentEditorUiState(
            agentName = "Some agent",
            hosts = listOf(
                HostConfigUiState(0L, "host1", OpenAi),
                HostConfigUiState(1L, "host2", OpenAi),
            ),
            selectedHost = 0,
            hostModels = listOf(
                ModelUiState(
                    id = 0L,
                    name = "model1"
                ),
                ModelUiState(
                    id = 1L,
                    name = "model2"
                )
            ),
            selectedModel = null,
            instructions = ""
        )

        /*
        When: A new agent view is displayed with given state
         */
        setContent {
            AgentEditorView(
                state = state,
                onSelectHost = { throw Exception("onSelectHost should not be called") },
                onSubmit = { name, modelIndex, instructions ->
                    actualName = name
                    actualModel = modelIndex
                    actualInstructions = instructions
                }
            )
        }

        /*
        Then: The agent name field is empty
        And: The host selector is enabled
        And: the model selector is enabled
        And: the submit button is disabled
         */
        agentNameField.assertEditableTextEquals("Some agent")
        hostSelector.assertIsEnabled()
        modelSelector.assertIsEnabled()
        submitButton.assertIsNotEnabled()

        /*
        When: The user opens the model selector
        And: The user selects a model
         */
        modelSelector.performClick()
        onNodeWithText("model2").performClick()

        /*
        Then: The selected model is reflected
        And: the submit button is enabled
         */
        onNodeWithText("model2")
            .assertExists()
        submitButton.assertIsEnabled()

        /*
        When: The user taps the submit button
         */
        onNodeWithText("Submit").performClick()

        /*
        Then: The onSubmit callback is called with the agent name, host, model, and instructions
         */
        assertEquals("Some agent", actualName)
        assertEquals(1, actualModel)
        assertEquals("", actualInstructions)
    }
}
