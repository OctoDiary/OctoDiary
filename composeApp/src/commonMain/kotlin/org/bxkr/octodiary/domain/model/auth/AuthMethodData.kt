package org.bxkr.octodiary.domain.model.auth

sealed class AuthMethodData {
    data class GoToUrl(
        val url: String,
        val isWebView: Boolean,
        val webViewListener: ((String) -> Boolean)? = null
    ) : AuthMethodData()

    data object Proceed : AuthMethodData()
}