package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.artes.entities.Google
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.viewmodels.HostConfigListUiState
import org.pointyware.artes.viewmodels.HostConfigUiState

/**
 *
 */
@Preview
@Composable
fun HostConfigListViewPreview() {
    HostConfigListView(
        state = HostConfigListUiState(
            hostConfigs = listOf(
                HostConfigUiState(
                    id = 0L,
                    title = "OpenAi Prod",
                    OpenAi
                ),
                HostConfigUiState(
                    id = 1L,
                    title = "OpenAi Stage",
                    OpenAi
                ),
                HostConfigUiState(
                    id = 2L,
                    title = "VertexAi Prod",
                    Google
                ),
                HostConfigUiState(
                    id = 3L,
                    title = "VertexAi Stage",
                    Google
                ),
            )
        ),
        onRegisterService = {}
    )
}
