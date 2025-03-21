package org.pointyware.artes.shared.workbench
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.MenuBarScope

/**
 *
 */
@Composable
fun MenuBarScope.WindowMenu(
    onClickDashboard: () -> Unit,
): Unit = this.Menu(
    text = "Window",
    mnemonic = 'W'
) {
    Item(text = "Minimize", onClick = { /* TODO: hide application in system tray */ })
    Item(text = "Maximize", onClick = { /* TODO: expand window to fill entire screen */ })
    Item(text = "Close", onClick = { /* TODO: close current window without exiting entire application */ })
    Separator()
    Item(text = "Dashboard", onClick = onClickDashboard)
}
