package org.bxkr.octodiary.domain.model.auth

sealed class AuthMethodData {
    data class GoToUrl(
        val url: String,
        val isWebView: Boolean,
    ) : AuthMethodData()

    data object Proceed : AuthMethodData()
}