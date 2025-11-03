package org.bxkr.octodiary.domain.model.auth

sealed class LogoutResult {
    data object Success : LogoutResult()

    data class Failure(
        val error: Throwable,
        val message: String
    ) : LogoutResult()
}