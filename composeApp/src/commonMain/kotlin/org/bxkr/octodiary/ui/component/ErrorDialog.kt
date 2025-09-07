package org.bxkr.octodiary.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.asClipEntry

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    message: String,
    representation: String,
    title: String? = null,
    isMessageHidden: Boolean = false,
    allowCopy: Boolean = true,
) {
    val localClipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismiss,
        {
            TextButton(onDismiss) { Text("Закрыть") }
        },
        dismissButton = if (allowCopy) {
            {
                TextButton({
                    coroutineScope.launch {
                        localClipboard.setClipEntry(message.asClipEntry())
                    }
                    onDismiss()
                }) {
                    Text("Скопировать в буфер")
                }
            }
        } else null,
        icon = {
            Icon(
                Icons.Rounded.Warning,
                null
            )
        },
        title = { Text(title ?: "Произошла ошибка!") },
        text = {
            Text(
                if (isMessageHidden) "Во время работы приложения произошла ошибка. Если проблема не исчезает со временем - свяжитесь с поддержкой" else representation,
                maxLines = 7,
                overflow = TextOverflow.Ellipsis,
                style = if (isMessageHidden) LocalTextStyle.current else MaterialTheme.typography.labelLarge
            )
        }
    )
}