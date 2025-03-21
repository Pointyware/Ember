package org.pointyware.artes.shared.workbench

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pointyware.artes.ui.components.CollapsibleColumn

data class SidebarState(
    val savedArchitectures: List<String>,
)

@Composable
fun rememberSidebarState(
    savedArchitectures: List<String> = emptyList()
) = remember(savedArchitectures) {
    SidebarState(
        savedArchitectures = savedArchitectures
    )
}

@Composable
fun SidebarSegment(
    title: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    CollapsibleColumn(
        modifier = modifier.width(IntrinsicSize.Max),
        header = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        },
        isExpanded = isExpanded,
        content = content
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModelType(
    typeName: String,
    selectors: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        Text(
            text = typeName,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 12.dp, vertical = 2.dp)
        )
        FlowRow {
            selectors.forEach {
                ModelSelector(it)
            }
        }
    }
}

@Composable
fun Sidebar(
    state: SidebarState = rememberSidebarState(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState(0))
            .background(MaterialTheme.colorScheme.primaryContainer) // TODO: use adaptive color
    ) {
        Text(
            text = "Select a model to start a workflow",
            style = MaterialTheme.typography.titleLarge,
        )
        SidebarSegment(
            title = "AI Models",
            isExpanded = true
        ) {
            ModelType(
                typeName = "Text",
                selectors = listOf(
                    "Chat",
                    "Code",
                    "Completion",
                    "Edit",
                )
            )
            ModelType(
                typeName = "Image",
                selectors = listOf(
                    "Text2Image",
                    "Image2Text",
                    "Image2Image",
//                    "Classification",
//                    "Detection",
//                    "Segmentation",
//                    "Generation",
//                    "Super Resolution",
//                    "Style Transfer",
                )
            )
            ModelType(
                typeName = "Video",
                selectors = listOf(
                    "Text2Video",
                    "Video2Text",
                )
            )
            ModelType(
                typeName = "Audio",
                selectors = listOf(
                    "Text2Audio",
                    "Audio2Text",
                )
            )
        }
        SidebarSegment(
            title = "Model Parameters",
            isExpanded = true
        ) {
            Text("Modify Model Parameters, Inputs, and Outputs")


        }
        SidebarSegment(
            title = "Saved Workflows",
            isExpanded = true
        ) {
            Text("Select a model configuration to use")


        }
        SidebarSegment(
            title = "AI Process Architectures",
            isExpanded = true
        ) {
            Text("Select an ai system architecture to use")

            if (state.savedArchitectures.isEmpty()) {
                Text("No architectures saved")
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = { /* Save current workspace as new architecture */ },
                ) {
                    Text("Save New Architecture")
                }
            } else {
                Column {
                    state.savedArchitectures.forEach { architecture ->
                        Text(architecture)
                    }
                }
            }
        }
    }
}
