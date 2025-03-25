package org.pointyware.artes.ui.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalTestApi::class)
class OptionsSelectorUnitTest {

    private var actualSelection: Int? = null

    @BeforeTest
    fun setUp() {
        actualSelection = null
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun selector_with_no_options_is_disabled() = runComposeUiTest {
        /*
        Given: Empty state
         */
        val state = OptionSelectorState.Empty

        /*
        When: The selector is rendered
         */
        setContent {
            OptionSelector(state = state, onOptionSelected = {actualSelection = it})
        }

        /*
        Then: The selector is disabled
        And: The actual selection is still null
         */
        onNodeWithText("Select an Option")
            .assertExists()
            .assertIsNotEnabled()
        assertNull(actualSelection)
    }

    @Test
    fun selection_with_options_is_enabled() = runComposeUiTest {
        /*
        Given: State with options
         */
        val state = OptionSelectorState.WithOptions(
            options = listOf("Option 1", "Option 2", "Option 3")
        )

        /*
        When: The selector is rendered
         */
        setContent {
            OptionSelector(state = state, onOptionSelected = {actualSelection = it})
        }

        /*
        Then: The selector is enabled
        And: The actual selection is still null
         */
        onNodeWithText("Select an Option")
            .assertExists()
            .assertIsEnabled()
        assertNull(actualSelection)
    }

    @Test
    fun selection_with_no_selected_option_is_labeled() = runComposeUiTest {
        /*
        Given: State with options and no selected option
         */
        val state = OptionSelectorState.WithOptions(
            options = listOf("Option 1", "Option 2", "Option 3")
        )

        /*
        When: The selector is rendered
         */
        setContent {
            OptionSelector(state = state, onOptionSelected = {actualSelection = it})
        }

        /*
        Then: The selector is enabled
        And: The actual selection is still null
         */
        onNodeWithText("Select an Option")
            .assertExists()
            .assertIsEnabled()
        assertNull(actualSelection)
    }

    @Test
    fun selection_with_selected_option_is_labeled() = runComposeUiTest {
        /*
        Given: State with options and a selected option
         */
        val state = OptionSelectorState.WithOptionsAndSelection(
            options = listOf("Option 1", "Option 2", "Option 3"),
            selectedOption = 1
        )

        /*
        When: The selector is rendered
         */
        setContent {
            OptionSelector(state = state, onOptionSelected = {actualSelection = it})
        }

        /*
        Then: The selector is enabled
        And: The actual selection is still null
         */
        onNodeWithText("Option 2")
            .assertExists()
            .assertIsEnabled()
        assertNull(actualSelection)
    }

    @Test
    fun tapping_option_selects_it() = runComposeUiTest {
        /*
        Given: State with options and a selected option
         */
        val state = OptionSelectorState.WithOptions(
            options = listOf("Option 1", "Option 2", "Option 3")
        )

        /*
        When: The selector is rendered
         */
        setContent {
            OptionSelector(state = state, onOptionSelected = {actualSelection = it})
        }

        /*
        Then: The dropdown menu is visible
         */
        onNodeWithText("Select an Option")
            .assertExists()
            .assertIsEnabled()
            .performClick()

        /*
        When: An option is selected
         */
        onNodeWithText("Option 2")
            .assertExists()
            .assertIsEnabled()
            .performClick()

        /*
        Then: The dropdown menu is hidden
        And: The actual selection is updated
         */
        onNodeWithText("Option 1")
            .assertDoesNotExist()
        onNodeWithText("Option 2")
            .assertDoesNotExist()
        onNodeWithText("Option 3")
            .assertDoesNotExist()
        assertEquals(1, actualSelection)
    }
}
