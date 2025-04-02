package org.pointyware.artes.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Preview
@Composable
fun OptionSelectorPreview(
    @PreviewParameter(OptionSelectorStateProvider::class) state: OptionSelectorState,
) {
    var currentState by remember { mutableStateOf(state) }
    OptionSelector(
        state = currentState,
        onOptionSelected = { currentState = OptionSelectorState.WithOptionsAndSelection(state.options, it) },
    )
}

class OptionSelectorStateProvider(): PreviewParameterProvider<OptionSelectorState> {
    override val values: Sequence<OptionSelectorState>
        get() = sequenceOf(
            OptionSelectorState.Empty,
            OptionSelectorState.WithOptions(listOf("Option 1", "Option 2")),
            OptionSelectorState.WithOptionsAndSelection(
                options = listOf("Option 1", "Option 2"),
                selectedOption = 0,
            ),
        )
}
