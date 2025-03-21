package org.pointyware.artes.shared.workbench

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.artes.ui.theme.ArtesTheme

@Preview
@Composable
fun SidebarPreview() {
    ArtesTheme(
        isDark = false
    ) {
        Surface {
            Sidebar(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                state = rememberSidebarState(
                    savedArchitectures = listOf(
                        "Censoring Workflow",
                        "Beautiful Landscape Workflow",
                    )
                )
            )
        }
    }
}
