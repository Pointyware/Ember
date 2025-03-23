package org.pointyware.artes.hosts.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "", showBackground = true)
@Preview(name = " (Night)", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewHostPreview() {
    NewHostView(
        modifier = Modifier.fillMaxSize()
    ) { _, _, _ -> }
}
