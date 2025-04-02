package org.pointyware.artes.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class AgentCreationViewState(
    val name: String,
    val description: String,
    val serviceSelectionState: ServiceSelectionDropDownState
)

/**
 * Displays elements to create a new agent or edit an existing one.
 *
 */
@Composable
fun AgentCreationView(
    state: AgentCreationViewState,
    modifier: Modifier = Modifier,
    onNameChange: (String)->Unit = {},
    onDescriptionChange: (String)->Unit = {},
    onSelectService: (Long)->Unit = {},
    onCancel: ()->Unit = {},
    onSave: ()->Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        TextField(
            value = state.name,
            label = { Text(text = "Name", style = MaterialTheme.typography.labelSmall) },
            onValueChange = onNameChange,
        )
        TextField(
            value = state.description,
            label = { Text(text = "Description", style = MaterialTheme.typography.labelSmall) },
            onValueChange = onDescriptionChange,
        )

        ServiceSelectionDropDown(
            state = state.serviceSelectionState,
            onSelectService = onSelectService
        )

        Row {
            Button(onClick = onCancel) {
                Text(text = "Cancel")
            }
            Button(onClick = onSave) {
                Text(text = "Save")
            }
        }
    }
}


data class ServiceSelectionDropDownState(
    val services: List<ServiceListItemState>,
    val selectedIndex: Int
)

@Composable
fun ServiceSelectionDropDown(
    state: ServiceSelectionDropDownState,
    modifier: Modifier = Modifier,
    onSelectService: (Long) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false },
        modifier = if (isExpanded) {
            modifier
        } else {
            modifier.clickable { isExpanded = true }
        }
    ) {
        state.services.forEachIndexed { index, service ->
            val selected = index == state.selectedIndex
            val itemModifier = Modifier.clickable { onSelectService(service.id) }
            ServiceListItem(
                state = service,
                modifier = if (selected) {
                    itemModifier.border(1.dp, Color.Black)
                } else itemModifier
            )
        }
    }
}
