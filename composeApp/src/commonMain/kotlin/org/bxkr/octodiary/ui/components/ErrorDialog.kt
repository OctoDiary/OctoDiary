package org.bxkr.octodiary.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.close
import kmpdiary.composeapp.generated.resources.copy_error_description
import kmpdiary.composeapp.generated.resources.internal_error_description
import kmpdiary.composeapp.generated.resources.internal_error_occurred
import kmpdiary.composeapp.generated.resources.log_out
import kmpdiary.composeapp.generated.resources.network_error_description
import kmpdiary.composeapp.generated.resources.network_error_occurred
import kmpdiary.composeapp.generated.resources.report_issue
import kmpdiary.composeapp.generated.resources.server_error_authorized
import kmpdiary.composeapp.generated.resources.server_error_not_authorized
import kmpdiary.composeapp.generated.resources.server_error_occurred
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.ExternalIntegration.TELEGRAM_REPORT_URL
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.auth.AuthManager
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ErrorDialog(error: Result.Error, onDismissRequest: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    val authManager: AuthManager = koinInject()
    val isAuthorized by authManager.isAuthorized.collectAsState(false)
    val coroutineScope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = { Button({ onDismissRequest() }) { Text(stringResource(Res.string.close)) } },
        dismissButton = if (error.errorType == Result.ErrorType.Server && isAuthorized) {
            {
                TextButton({
                    onDismissRequest()
                    coroutineScope.launch {
                        authManager.logOut("error dialog")
                    }
                }) { Text(stringResource(Res.string.log_out)) }
            }
        } else null,
        icon = { Icon(Icons.Rounded.Warning, null) },
        title = {
            Text(
                stringResource(
                    when (error.errorType) {
                        Result.ErrorType.Network -> Res.string.network_error_occurred
                        Result.ErrorType.Server -> Res.string.server_error_occurred
                        Result.ErrorType.Internal -> Res.string.internal_error_occurred
                    }
                ), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = Modifier.fillMaxWidth(),
        text = {
            when (error.errorType) {
                Result.ErrorType.Server, Result.ErrorType.Internal, Result.ErrorType.Network -> Column {
                    Text(
                        stringResource(
                            when (error.errorType) {
                                Result.ErrorType.Server -> if (isAuthorized) Res.string.server_error_authorized else Res.string.server_error_not_authorized
                                Result.ErrorType.Network -> Res.string.network_error_description
                                Result.ErrorType.Internal -> Res.string.internal_error_description
                            }
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            { clipboardManager.setText(AnnotatedString("copied from ErrorDialog\n\n" + error.description)) },
                            Modifier.padding(vertical = 8.dp),
                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                        ) {
                            Icon(
                                Icons.Rounded.ContentCopy,
                                null,
                                Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(stringResource(Res.string.copy_error_description))
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            { uriHandler.openUri(TELEGRAM_REPORT_URL) },
                            Modifier.padding(vertical = 8.dp),
                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                        ) {
                            Icon(
                                Icons.Rounded.BugReport,
                                null,
                                Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(stringResource(Res.string.report_issue))
                        }
                    }
                }

//                ErrorType.##### -> Text(
//                    text,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                ErrorType.Client -> Column {
//                    Text(
//                        R.string.bad_connection,
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                    Row(
//                        Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        OutlinedButton(
//                            { clipboardManager.setText(AnnotatedString(text)) },
//                            Modifier.padding(vertical = 8.dp),
//                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
//                        ) {
//                            Icon(
//                                Icons.Rounded.ContentCopy,
//                                null,
//                                Modifier.size(ButtonDefaults.IconSize)
//                            )
//                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//                            Text("Скопировать текст ошибки")
//                        }
//                    }
//                }
            }
        }
    )
}