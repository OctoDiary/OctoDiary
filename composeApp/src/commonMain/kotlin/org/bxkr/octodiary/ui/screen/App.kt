package org.bxkr.octodiary.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.bxkr.octodiary.ui.component.DebugMenu
import org.bxkr.octodiary.ui.screen.auth.AuthScreen
import org.bxkr.octodiary.ui.screen.auth.CallbackScreen
import org.bxkr.octodiary.ui.screen.diary.NavScreen
import org.bxkr.octodiary.ui.theme.OctoDiaryTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun App() {
    val mainViewModel: MainViewModel = koinViewModel()
    val authViewModel: AuthViewModel = koinViewModel()
    val uiState by mainViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.authState) {
        if (uiState.authState is AuthState.Authorized) {
            authViewModel.resetUiState()
        }
    }

    OctoDiaryTheme {
        Surface {
            Box {
                if (uiState.isDebugMenuOpened) DebugMenu { mainViewModel.closeDebugMenu() }
                uiState.authState.run {
                    AnimatedVisibilityFade(this is AuthState.NotAuthorized || this is AuthState.Callback.WaitingForCallback) {
                        AuthScreen()
                    }
                    AnimatedVisibilityFade(this is AuthState.Authorized) {
                        NavScreen()
                    }
                    AnimatedVisibilityFade(this is AuthState.Callback.HandlingCallback || this is AuthState.Callback.FailedToHandle) {
                        (this as? AuthState.Callback)?.let { CallbackScreen(it) }
                    }
                    AnimatedVisibilityFade(this == null) {
                        Surface {
                            Box(Modifier.fillMaxSize()) {
                                LoadingIndicator(Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }
}