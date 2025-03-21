package org.pointyware.artes.shared.workbench

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 *
 */
@Composable
fun ModelSelector(
    modelName: String,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 6.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 12.dp,
            draggedElevation = 8.dp,
            disabledElevation = 2.dp
        )
    ) {
        Text(
            text = modelName,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp),
        )
    }
}
