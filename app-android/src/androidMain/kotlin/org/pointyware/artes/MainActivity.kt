package org.pointyware.artes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.pointyware.ember.ui.EmberApp
import org.pointyware.ember.ui.theme.EmberTheme

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
            EmberTheme {
                EmberApp()
            }
        }
    }
}

@Preview
@Composable
fun AndroidAppPreview() {
    EmberTheme {
        EmberApp()
    }
}
