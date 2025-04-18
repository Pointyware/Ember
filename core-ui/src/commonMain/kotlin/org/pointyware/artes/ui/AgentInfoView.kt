package org.pointyware.artes.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.pointyware.artes.viewmodels.HostConfigUiState

/**
 *
 */
data class AgentInfoViewState(
    val name: String,
    val description: String,
    val hostConfig: HostConfigUiState
)

/**
 *
 */
@Composable
fun AgentInfoView(
    state: AgentInfoViewState,
    modifier: Modifier = Modifier,
    onDelete: ()->Unit,
    onEdit: ()->Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = state.description,
            style = MaterialTheme.typography.labelMedium
        )

        HostConfigListItem(
            modifier = Modifier.border(1.dp, Color.Black),
            state = state.hostConfig,
        )

        Row {
            Button(onClick = onEdit) {
                Text(text = "Edit")
            }
            Button(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    }
}
