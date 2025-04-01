package org.pointyware.artes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.pointyware.artes.shared.ui.ArtesApp
import org.pointyware.artes.ui.theme.ArtesTheme

/**
 * Main entry-point for the Android application. We plan to follow the single-activity app model,
 * using the shared composables for the UIs, but we are not restricting ourselves from using other
 * Android components to extend the functionality of the app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtesTheme {
                ArtesApp()
            }
        }
    }
}
