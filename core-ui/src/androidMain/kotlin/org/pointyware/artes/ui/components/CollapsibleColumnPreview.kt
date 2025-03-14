package org.pointyware.artes.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CollapsibleColumnPreview() {
    Column(
        modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        CollapsibleColumn(
            modifier = Modifier
                .border(2.dp, Color.Black)
                .padding(8.dp)
                .fillMaxWidth(),
            header = { Text("Hi There!") },
            isExpanded = true
        ) {
            Text("This content should be visible")
        }
        CollapsibleColumn(
            modifier = Modifier
                .border(2.dp, Color.Black)
                .padding(8.dp)
                .fillMaxWidth(),
            header = { Text("Hi There!") },
            isExpanded = false
        ) {
            Text("This content should not be visible")
        }
    }
}
