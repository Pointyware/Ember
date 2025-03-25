package org.pointyware.artes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

sealed class OptionSelectorState(
    val options: List<String>,
    open val selectedOption: Int?,
) {
    data object Empty : OptionSelectorState(emptyList(), null)
    open class WithOptions(
        options: List<String>,
    ) : OptionSelectorState(options, null)
    class WithOptionsAndSelection(
        options: List<String>,
        override val selectedOption: Int,
    ) : OptionSelectorState(options, selectedOption)
}

@Composable
fun rememberOptionSelectorState(
    options: List<String>,
    selectedOption: Int?,
): OptionSelectorState {
    return remember { if (options.isEmpty()) {
        OptionSelectorState.Empty
    } else {
        if (selectedOption == null) {
            OptionSelectorState.WithOptions(options)
        } else {
            OptionSelectorState.WithOptionsAndSelection(options, selectedOption)
        }
    } }
}

/**
 * Provides a standard implementation of the DropdownMenu composable with a button labeled by the
 * current selection.
 *
 * When no options are provided, the button is disabled entirely.
 * When no option is selected, the button is labeled "Select an Option".
 * When an option is selected, the button is labeled by the selected option.
 */
@Composable
fun OptionSelector(
    state: OptionSelectorState,
    modifier: Modifier = Modifier,
    onOptionSelected: (Int)->Unit,
) {
    Box {
        var showOptions by remember { mutableStateOf(false) }
        Button(
            modifier = modifier,
            onClick = { showOptions = true },
            enabled = state !is OptionSelectorState.Empty
        ) {
            val label = remember(state) { when (state) {
                is OptionSelectorState.WithOptionsAndSelection -> { state.options[state.selectedOption] }
                else -> "Select an Option"
            } }
            Text(label)
        }
        DropdownMenu(
            expanded = showOptions,
            onDismissRequest = { showOptions = false },
        ) {
            state.options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        showOptions = false
                        onOptionSelected(index)
                    }
                )
            }
        }
    }
}
