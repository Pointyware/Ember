package org.pointyware.artes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 */
@Preview
@Composable
fun ServiceListViewPreview() {
    ServiceListView(
        state = ServiceListViewState(
            services = listOf(
                ServiceListItemState(
                    id = 0L,
                    label = "OpenAi Prod",
                    service = "OpenAi"
                ),
                ServiceListItemState(
                    id = 1L,
                    label = "OpenAi Stage",
                    service = "OpenAi"
                ),
                ServiceListItemState(
                    id = 2L,
                    label = "VertexAi Prod",
                    service = "VertexAi"
                ),
                ServiceListItemState(
                    id = 3L,
                    label = "VertexAi Stage",
                    service = "VertexAi"
                ),
            )
        ),
        onRegisterService = {}
    )
}
