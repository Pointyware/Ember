package org.pointyware.ember.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.ember.viewmodels.DefaultColorMap

@Preview
@Composable
private fun NeuronViewPreview() {
    NeuronView(
        weights = listOf(
            0.1f, -0.2f, 0.3f, -0.4f, 0.5f, -0.6f, 0.7f, -0.8f, 0.9f, -1.0f
        ),
        bias = 0.5f,
        colorMap = DefaultColorMap
    )
}
