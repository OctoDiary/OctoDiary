package org.bxkr.octodiary.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DebugMenu(onDismiss: () -> Unit) {
    AlertDialog(
        onDismiss,
        confirmButton = {
            TextButton(onDismiss) {
                Text("Закрыть")
            }
        },
        title = { Text("Меню отладки") }
    )
}