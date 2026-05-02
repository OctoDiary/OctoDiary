package org.bxkr.octodiary.data.gateway

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.auth.*
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.koin.core.annotation.Single

@Single
class MesTatarstanAuthGateway : AuthGateway {
    override val responsibleFor
        get() = DiaryId.MesTatarstan

    override suspend fun processAuthStep(credentials: Credentials): AuthStepResult =
        when (credentials) {
            is Credentials.FreshAuth -> initializeAuth(credentials.authMethod)
            is Credentials.AccessToken -> authorizeByToken(credentials.accessToken)
        }

    override fun handleCallback(
        callbackLink: String,
        method: AuthMethod
    ): Flow<CallbackState> {
        TODO("Not yet implemented")
    }

    override fun checkToken(token: String): Flow<TokenInfo> {
        TODO("Not yet implemented")
    }

    private fun initializeAuth(authMethod: AuthMethod): AuthStepResult {
        TODO("Not yet implemented")
    }

    private fun authorizeByToken(token: String): AuthStepResult {
        TODO("Not yet implemented")
    }
}