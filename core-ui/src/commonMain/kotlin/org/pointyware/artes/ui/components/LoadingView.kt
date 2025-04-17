package org.pointyware.artes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.pointyware.artes.viewmodels.LoadingUiState

/**
 * Reflects the given [state] by showing a loading indicator or an error message.
 */
@Composable
fun LoadingView(
    state: LoadingUiState,
    modifier: Modifier = Modifier,
    onClearError: ()->Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is LoadingUiState.Idle -> {}
            is LoadingUiState.Loading -> {
                Text("Loading...")
            }
            is LoadingUiState.Error -> {

                Dialog(
                    onDismissRequest = onClearError
                ) {
                    Text(
                        text = state.message,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
