package org.pointyware.artes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.pointyware.artes.viewmodels.AgentListUiState

/**
 */
data class AgentListItemState(
    val id: Long,
    val name: String,
    val host: String,
    val model: String
)

/**
 * Displays basic agent information: name, service, and model
 */
@Composable
fun AgentListItem(
    state: AgentListItemState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = state.name,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.weight(1f),
            text = state.host,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = state.model,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

data class AgentListViewState(
    val agents: List<AgentListItemState>
)

fun AgentListUiState.mapToViewState(): AgentListViewState {
    return AgentListViewState(
        agents = this.agents.map {
            AgentListItemState(
                id = it.id,
                name = it.name,
                host = it.host.title,
                model = it.model.name
            )
        }
    )
}

/**
 * Displays a list of agents that a user can select to interact with.
 * - Allows users to add a new agent
 */
@Composable
fun AgentListView(
    state: AgentListViewState,
    modifier: Modifier = Modifier,
    onCreateAgent: ()->Unit,
    onSelectAgent: (Long)->Unit,
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.agents) {
                AgentListItem(
                    state = it,
                    modifier = Modifier.clickable { onSelectAgent(it.id) }
                )
            }
        }

        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onCreateAgent
        ) {
            Text(
                text = "New Agent",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
