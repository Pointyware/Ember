package org.pointyware.artes.ui

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


/**
 * Redundant with [HostUiState]
 */
data class ServiceListItemState(
    val id: Long,
    val label: String,
    val service: String,
)

@Composable
fun ServiceListItem(
    state: ServiceListItemState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = state.label,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = state.service,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

data class ServiceListViewState(
    val services: List<ServiceListItemState>
)


/**
 *
 */
@Composable
fun ServiceListView(
    state: ServiceListViewState,
    modifier: Modifier = Modifier,
    onRegisterService: ()->Unit,
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.services) {
                ServiceListItem(it)
            }
        }

        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onRegisterService
        ) {
            Text(
                text = "Register Service"
            )
        }
    }
}
