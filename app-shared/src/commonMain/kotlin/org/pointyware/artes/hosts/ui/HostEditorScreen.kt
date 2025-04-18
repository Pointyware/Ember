package org.pointyware.artes.hosts.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.pointyware.artes.hosts.viewmodels.HostViewModel

/**
 *
 */
@Composable
fun HostEditorScreen(
    viewModel: HostViewModel,
    onBack: ()->Unit,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onHostCreated.collect {
            onBack()
        }
    }
    HostEditorView(
        state = state,
        modifier = Modifier.fillMaxSize(),
        onCreateHost = viewModel::createHost
    )
}
