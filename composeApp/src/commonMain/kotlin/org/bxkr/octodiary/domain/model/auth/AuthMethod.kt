package org.bxkr.octodiary.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AuthMethod {
    @Serializable
    sealed class InBrowser : AuthMethod() {
        @Serializable
        @SerialName("inbrowser-mosru")
        data object MosRu : InBrowser()
        @Serializable
        @SerialName("inbrowser-esia")
        data object Esia : InBrowser()
    }
    @Serializable
    sealed class WebView : AuthMethod() {
        @Serializable
        @SerialName("webview-mosru")
        data object MosRu : WebView()
        @Serializable
        @SerialName("webview-esia")
        data object Esia : WebView()
    }

    @Serializable
    @SerialName("login-pasword")
    data object LoginPassword : AuthMethod()
    @Serializable
    @SerialName("telegram")
    data object Telegram : AuthMethod()
    @Serializable
    @SerialName("access-token")
    data class AccessToken(
        val supportedTokenFormatsHint: List<TokenFormat>? = null
    ) : AuthMethod() {
        @Serializable
        sealed class TokenFormat {
            @Serializable
            @SerialName("school-mos")
            data object School : TokenFormat()
            @Serializable
            @SerialName("uchebnik-mos")
            data object Uchebnik : TokenFormat()
        }
    }
}