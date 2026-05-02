package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.component.ErrorDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    mainViewModel: MainViewModel = koinViewModel()
) {
    val viewModel: AuthViewModel = koinViewModel()
    val mainUiState by mainViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    val pagerState = rememberPagerState { 4 }

    LaunchedEffect(mainUiState.authState) {
        if (mainUiState.authState == AuthState.NotAuthorized) {
            viewModel.resetUiState()
        }
    }

    LaunchedEffect(uiState.currentPage) {
        pagerState.animateScrollToPage(uiState.currentPage)
    }

    LaunchedEffect(uiState.openLink) {
        if (uiState.openLink != null) {
            if (uiState.openLink?.isWebView == false)
                uiState.openLink?.url?.let { uriHandler.openUri(it) }
            else if (uiState.openLink?.isWebView == true)
                uiState.openLink?.let { viewModel.openWebViewPage(it) }
        }
    }

    if (uiState.error != null) {
        ErrorDialog({ viewModel.discardError() }, uiState.error?.message ?: "null error", getErrorRepresentation(uiState.error))
    }

    HorizontalPager(
        pagerState,
        userScrollEnabled = false
    ) { pageIndex ->
        when (pageIndex) {
            0 -> AuthSelectRegion()
            1 -> AuthSelectDiary()
            2 -> AuthSelectMethod()
            3 -> AuthAdditionalPage()
        }
    }
}

@Composable
private fun getErrorRepresentation(error: AuthViewModel.ErrorDescription?): String = when (error) {
    is AuthViewModel.ErrorDescription.ConnectionError -> "Ошибка подключения. Проверьте интернет-соединение, состояние VPN и прочее"
    else -> error?.message ?: "Информация об ошибке неизвестна"
}