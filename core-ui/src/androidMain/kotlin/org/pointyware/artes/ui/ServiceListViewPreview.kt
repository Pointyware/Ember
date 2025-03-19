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
                    id = "0",
                    label = "OpenAi Prod",
                    service = "OpenAi"
                ),
                ServiceListItemState(
                    id = "1",
                    label = "OpenAi Stage",
                    service = "OpenAi"
                ),
                ServiceListItemState(
                    id = "2",
                    label = "VertexAi Prod",
                    service = "VertexAi"
                ),
                ServiceListItemState(
                    id = "3",
                    label = "VertexAi Stage",
                    service = "VertexAi"
                ),
            )
        ),
        onRegisterService = {}
    )
}
