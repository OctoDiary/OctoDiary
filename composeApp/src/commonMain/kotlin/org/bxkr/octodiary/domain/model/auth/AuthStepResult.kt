package org.bxkr.octodiary.domain.model.auth

sealed class AuthStepResult {
    data class Success(val accessCredentials: AccessCredentials) : AuthStepResult()

    data class ProceedWithAuthMethod(
        val authMethodData: AuthMethodData
    ) : AuthStepResult()

    data class Failure(
        val error: Throwable,
        val message: String
    ) : AuthStepResult()
}