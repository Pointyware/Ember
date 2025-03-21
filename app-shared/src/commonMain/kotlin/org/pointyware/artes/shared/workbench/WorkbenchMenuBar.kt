package org.pointyware.artes.shared.workbench

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

@Composable
fun FrameWindowScope.WorkbenchMenuBar(
    onOpenSettings: () -> Unit,
    onOpenDashboard: () -> Unit,
) = this.MenuBar {
    Menu(
        text = "File",
        mnemonic = 'F'
    ) {
        Item(text = "New Project", onClick = { /* TODO: trigger project creation */ })
        Item(text = "Open Project", onClick = { /* TODO: trigger file selector with project filter */ })
        Item(text = "Save Project", onClick = { /* TODO: trigger project saving */ })
        Item(text = "Save Project As", onClick = { /* TODO: trigger manual project saving */ })
        Item(text = "Import", onClick = { /* TODO: trigger project importing? from what? URL? */ })
        Item(text = "Export", onClick = { /* TODO: trigger project exporting? publication to repo? */ })
        Item(text = "Exit", onClick = { /* TODO: close app; prompt to save unsaved work? */ })
    }
    Menu(
        text = "Edit",
        mnemonic = 'E'
    ) {
        Item(text = "Undo", onClick = { /* TODO: undo last action (saved as command) */ })
        Item(text = "Redo", onClick = { /* TODO: redo last undone action (saved as command) */ })
        Item(text = "Cut", onClick = { /* TODO: store and remove currently selected elements */ })
        Item(text = "Copy", onClick = { /* TODO: store currently selected elements */ })
        Item(text = "Paste", onClick = { /* TODO: recreate elements currently stored in clipboard */ })
        Item(text = "Preferences", onClick = onOpenSettings)
    }
    Menu(
        text = "View",
        mnemonic = 'V'
    ) {
        Item(text = "Zoom In", onClick = { /* TODO: increase zoom level */ })
        Item(text = "Zoom Out", onClick = { /* TODO: decrease zoom level */ })
        Item(text = "Reset Zoom", onClick = { /* TODO: return zoom level to default level */ })
        Item(text = "Full Screen Mode", onClick = { /* TODO: enter/exit fullscreen mode */ })
    }
    WindowMenu(
        onClickDashboard = onOpenDashboard
    )
    Menu(
        text = "Help",
        mnemonic = 'H'
    ) {
        Item(text = "Documentation", onClick = { /* TODO: open documentation; internal or external? */ })
        Item(text = "Tutorials", onClick = { /* TODO: open tutorials resources; internal or external? */ })
        Item(text = "Check for Updates", onClick = { /* TODO: connect to public endpoint for updates */ })
        Item(text = "About", onClick = { /* TODO: open about info window */ })
    }
}
