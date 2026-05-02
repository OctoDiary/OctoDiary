package org.bxkr.octodiary.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DebugMenu(onDismiss: () -> Unit) {
    AlertDialog(
        onDismiss,
        confirmButton = {
            TextButton(onDismiss) {
                Text("Закрыть")
            }
        },
        title = { Text("Меню отладки") },
        text = {
            Button({
                onDismiss()

            }) {
                Text("Войти на другой сервер")
            }
        }
    )
}