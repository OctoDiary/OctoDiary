package org.bxkr.octodiary.domain.model.auth

sealed class Credentials {
    data class FreshAuth(
        val authMethod: AuthMethod
    ) : Credentials()

    data class AccessToken(
        val accessToken: String
    ) : Credentials()
}