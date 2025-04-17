package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.artes.entities.Google
import org.pointyware.artes.entities.OpenAi
import org.pointyware.artes.viewmodels.HostConfigListUiState
import org.pointyware.artes.viewmodels.HostUiState

/**
 *
 */
@Preview
@Composable
fun ServiceListViewPreview() {
    ServiceListView(
        state = HostConfigListUiState(
            hostConfigs = listOf(
                HostUiState(
                    id = 0L,
                    title = "OpenAi Prod",
                    OpenAi
                ),
                HostUiState(
                    id = 1L,
                    title = "OpenAi Stage",
                    OpenAi
                ),
                HostUiState(
                    id = 2L,
                    title = "VertexAi Prod",
                    Google
                ),
                HostUiState(
                    id = 3L,
                    title = "VertexAi Stage",
                    Google
                ),
            )
        ),
        onRegisterService = {}
    )
}
