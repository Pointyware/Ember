package org.pointyware.artes.hosts.ui

import androidx.compose.runtime.Composable
import org.pointyware.artes.hosts.viewmodels.HostViewModel

/**
 *
 */
@Composable
fun HostEditorScreen(
    viewModel: HostViewModel,
) {
    val state by viewModel.state.collectAsState()
    HostEditorView(
        state = state,
        modifier = modifier,
        onCreateHost = hostViewModel::createHost
    )
}
