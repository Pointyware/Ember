package org.pointyware.artes.hosts.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.artes.hosts.viewmodels.ExtraOptionsUiState
import org.pointyware.artes.hosts.viewmodels.HostConfigEditorUiState
import org.pointyware.artes.ui.theme.ArtesTheme
import org.pointyware.artes.viewmodels.LoadingUiState

@Preview(name = "New Host", showBackground = true)
@Preview(name = "New Host (Night)", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewHostPreview() {
    ArtesTheme {
        Surface {
            HostEditorView(
                state = HostConfigEditorUiState(
                    title = "Some Host",
                    extraOptions = ExtraOptionsUiState.OpenAi(
                        orgId = "0987654321",
                        apiKey = "1234567890",
                    ),
                    loading = LoadingUiState.Idle,
                ),
                modifier = Modifier.fillMaxSize(),
                onCreateHost = { _, _ -> }
            )
        }
    }
}
