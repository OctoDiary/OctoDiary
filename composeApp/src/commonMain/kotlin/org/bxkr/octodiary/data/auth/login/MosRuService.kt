package org.bxkr.octodiary.data.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import io.github.xxfast.kstore.KStore
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.loading_handle_code_step1
import kmpdiary.composeapp.generated.resources.loading_handle_code_step2
import kmpdiary.composeapp.generated.resources.loading_handle_code_title
import kmpdiary.composeapp.generated.resources.loading_register_session_step1
import kmpdiary.composeapp.generated.resources.loading_register_session_title
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.MosRuInfo
import org.bxkr.octodiary.data.RefreshInfo
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.auth.AuthManager
import org.bxkr.octodiary.data.auth.token.MesToken
import org.bxkr.octodiary.encodeToBase64
import org.bxkr.octodiary.hash
import org.bxkr.octodiary.model.api.login.mosru.TokenExchange
import org.bxkr.octodiary.model.internal.ClientCredentials
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.bxkr.octodiary.ui.components.DescribedLoading
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * This class describes
 * - ↓ auth method "mos.ru" (both main and webview variants)
 * - ↓ for system "MES"
 * - ↓ in region "Moscow"
 */
class MosRuService : KoinComponent {
    private val mosAuthClient: MosAuthClient by inject()
    private val kStore: KStore<StorageLatest> by inject()
    private val authManager: AuthManager by inject()

    private companion object {
        const val MOS_AUTH_GATE_URL = "https://login.mos.ru/sps/oauth/ae"

        // DO NOT EDIT. THESE ARE STATIC API VALUES
        const val SCOPE =
            "birthday contacts openid profile snils blitz_change_password blitz_user_rights blitz_qr_auth"
        const val RESPONSE_TYPE = "code"
        const val PROMPT = "login"
        const val BIP_ACTION_HINT = "used_sms"
        const val REDIRECT_URI = "dnevnik-mes://oauth2redirect"
        const val ACCESS_TYPE = "offline"
        const val CODE_CHALLENGE_METHOD = "S256"
    }

    suspend fun startAuthInBrowser(uriHandler: UriHandler): Result<*> {
        val callResult = mosAuthClient.issueCall()
        if (callResult is Result.Success) {
            val url = getUrl(callResult.value)
            uriHandler.openUri(url)
        }
        return callResult
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '_' + '-'
        return (1..length).map { allowedChars.random() }.joinToString("")
    }

    private suspend fun getUrl(credentials: ClientCredentials): String {
        val codeVerifier = getRandomString(80)
        val codeChallenge = encodeToBase64(hash(codeVerifier))

        kStore.update {
            it?.copy(
                mosRuInfo = MosRuInfo(
                    clientId = credentials.clientId,
                    clientSecret = credentials.clientSecret,
                    codeVerifier = codeVerifier
                )
            )
        }

        val uri = URLBuilder(Url(MOS_AUTH_GATE_URL))
        uri.parameters.apply {
            append("scope", SCOPE)
            append("access_type", ACCESS_TYPE)
            append("response_type", RESPONSE_TYPE)
            append("client_id", credentials.clientId)
            append("redirect_uri", REDIRECT_URI)
            append("prompt", PROMPT)
            append("code_challenge", codeChallenge)
            append("code_challenge_method", CODE_CHALLENGE_METHOD)
            append("bip_action_hint", BIP_ACTION_HINT)
        }
        return uri.build().toString()
    }

    @Composable
    fun AuthWebView(needToRefresh: Boolean) {
        val url: MutableState<Result<String>> = remember { mutableStateOf(Result.Loading) }
        val coroutineScope = rememberCoroutineScope()
        val vm: AuthViewModel = koinViewModel()
        LaunchedEffect(needToRefresh) {
            url.value = Result.Loading
            coroutineScope.launch {
                val credentialsResult = mosAuthClient.issueCall()
                url.value = when (credentialsResult) {
                    is Result.Success -> Result.Success(getUrl(credentialsResult.value))
                    is Result.Error -> credentialsResult
                    else -> url.value
                }
            }
        }

        LaunchedEffect(url.value) {
            if (url.value !is Result.Success) vm.hideActions() else vm.showActions()
        }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AnimatedVisibility(url.value is Result.Success) {
                val success = url.value
                if (success is Result.Success) WebViewAuth(success.value)
            }
            AnimatedVisibility(url.value !is Result.Success) {
                DescribedLoading(
                    stringResource(Res.string.loading_register_session_title),
                    mapOf(
                        stringResource(Res.string.loading_register_session_step1) to url
                    )
                ) { vm.triggerRefresh() }
            }
        }
    }

    @Composable
    private fun WebViewAuth(url: String) {
        val webViewState = rememberWebViewState(url)
        var done by remember { mutableStateOf(false) }

        LaunchedEffect(webViewState.lastLoadedUrl) {
            if (webViewState.lastLoadedUrl?.startsWith("dnevnik-mes") == true) {
                done = true
            }
        }

        AnimatedVisibility(!done) {
            WebView(webViewState, Modifier.fillMaxSize())
        }
        AnimatedVisibility(done) {
            val url2 = webViewState.lastLoadedUrl
            if (url2 != null)
                AuthCode(url2)
            else Text("url2 is null?")
        }
    }

    @Composable
    fun AuthCode(url: String) {
        val coroutineScope = rememberCoroutineScope()
        var trigger by remember { mutableStateOf(false) }
        val code = Url(url).parameters["code"]
        val result1 = remember { mutableStateOf<Result<TokenExchange>?>(null) }
        val result2 = remember { mutableStateOf<Result<MesToken>?>(null) }
        val viewModel: AuthViewModel = koinViewModel()

        LaunchedEffect(trigger) {
            viewModel.hideActions()
            coroutineScope.launch {
                val mosRuInfo = kStore.get()?.mosRuInfo
                if (mosRuInfo != null && code != null) {
                    result1.value = Result.Loading
                    val call1 = mosAuthClient.handleCode(code, mosRuInfo)
                    result1.value = call1
                    if (call1 is Result.Success<TokenExchange>) {
                        kStore.update {
                            it?.copy(
                                refreshInfo = RefreshInfo(
                                    mosRuRefreshToken = call1.value.refreshToken
                                )
                            )
                        }

                        result2.value = Result.Loading
                        val call2 = mosAuthClient.mosToMes(call1.value.accessToken)
                        result2.value = call2
                        if (call2 is Result.Success<MesToken>) {
                            authManager.updateAccessToken(call2.value.value)
                            viewModel.authFinished()
                        }
                    }
                } else {
                    result1.value = Result.Error("mosRuInfo or code is null?")
                }
            }
        }

        DescribedLoading(
            stringResource(Res.string.loading_handle_code_title),
            mapOf(
                stringResource(Res.string.loading_handle_code_step1) to result1,
                stringResource(Res.string.loading_handle_code_step2) to result2
            )
        ) {
            result1.value = null
            result2.value = null
            trigger = !trigger
        }
    }
}