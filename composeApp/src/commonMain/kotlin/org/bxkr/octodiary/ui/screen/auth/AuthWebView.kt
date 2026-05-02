package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebContent
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthWebView(
    webViewParams: AuthViewModel.AdditionalPageContent.WebView,
    viewModel: AuthViewModel = koinViewModel()
) {
    val webViewState = remember { WebViewState(WebContent.Url(webViewParams.url)) }
    val currentUrl = webViewState.lastLoadedUrl
    LaunchedEffect(currentUrl) {
        if (currentUrl != null && webViewParams.webViewListener(currentUrl)) {
            viewModel.catchWebViewUrl(currentUrl)
        }
    }
    WebView(webViewState, Modifier.fillMaxSize())
}