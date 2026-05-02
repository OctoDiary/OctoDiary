package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.bxkr.octodiary.asClipEntry
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.domain.model.auth.CallbackState
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CallbackScreen(
    authState: AuthState.Callback,
    authViewModel: AuthViewModel = koinViewModel()
) {
    Box {
        AnimatedVisibilityFade(authState is AuthState.Callback.HandlingCallback) {
            val callbackState = (authState as? AuthState.Callback.HandlingCallback)?.callbackState
            var isDialogShown by remember { mutableStateOf(false) }

            if (isDialogShown) {
                AlertDialog(
                    {
                        isDialogShown = false
                    },
                    confirmButton = {
                        Button({
                            authViewModel.finishCallbackHandling()
                        }) {
                            Text("Остановить")
                        }
                    },
                    dismissButton = {
                        TextButton({
                            isDialogShown = false
                        }) {
                            Text("Отмена")
                        }
                    },
                    title = { Text("Остановить загрузку") },
                    text = {
                        Text("Обработка кода авторизации в процессе. Вы уверены, что хотите остановить загрузку и войти заново?")
                    }
                )
            }

            Scaffold(
                topBar = {
                    IconButton(
                        { isDialogShown = true },
                        Modifier.padding(16.dp).padding(top = 16.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Close,
                            "Закрыть"
                        )
                    }
                }
            ) { paddingValues ->
                Surface(Modifier.padding(paddingValues)) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            LoadingIndicator()
                            Spacer(Modifier.height(16.dp))
                            Text(
                                if (callbackState is CallbackState.Initialized) "Получен callback-код" else "Обрабатываю callback-код",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibilityFade(authState is AuthState.Callback.FailedToHandle) {
            val clipboard = LocalClipboard.current
            val coroutineScope = rememberCoroutineScope()
            val state = authState as? AuthState.Callback.FailedToHandle
            val errorDescription =
                "Failed to handle callback.\n" +
                        "Cause: ${state?.exception?.cause}\n" +
                        "Throwable message: ${state?.exception?.exceptionMessage}\n" +
                        "Stack trace: ${state?.exception?.exceptionStackTrace}"

            Scaffold(
                topBar = {
                    IconButton(
                        { authViewModel.finishCallbackHandling() },
                        Modifier.padding(16.dp)
                    ) {
                        Icons.AutoMirrored.Rounded.ArrowBack
                    }
                }
            ) { paddingValues ->
                Surface {
                    Box(Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Icon(
                                Icons.Rounded.Warning,
                                null
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Произошла ошибка при обработке callback-кода!",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton({
                                coroutineScope.launch {
                                    clipboard.setClipEntry(errorDescription.asClipEntry())
                                }
                            }) { Text("Скопировать подробности") }
                        }
                    }
                }
            }
        }
    }
}