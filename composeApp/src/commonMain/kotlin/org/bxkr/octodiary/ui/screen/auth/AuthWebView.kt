package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthWebView(
    webViewParams: AuthViewModel.AdditionalPageContent.WebView,
    viewModel: AuthViewModel = koinViewModel()
) {
    val webViewState = rememberWebViewState(webViewParams.url) {
        androidWebSettings.apply {
            useWideViewPort = true
            loadsImagesAutomatically = true
            domStorageEnabled = true
        }
    }
    val currentUrl = webViewState.lastLoadedUrl
    LaunchedEffect(currentUrl) {
        if (currentUrl != null && webViewParams.webViewListener(currentUrl)) {
            viewModel.catchWebViewUrl(currentUrl)
        }
    }
    Box(Modifier.fillMaxSize()) {
        WebView(webViewState, Modifier.fillMaxSize())
    }
}