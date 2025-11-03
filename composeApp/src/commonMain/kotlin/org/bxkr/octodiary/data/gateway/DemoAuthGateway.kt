package org.bxkr.octodiary.data.gateway

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.CallbackState
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.koin.core.annotation.Single

@Single
class DemoAuthGateway : AuthGateway {
    override val responsibleFor = DiaryId.Demo

    override suspend fun processAuthStep(credentials: Credentials): AuthStepResult {
        return AuthStepResult.Success(AccessCredentials.DemoAccessCredentials)
    }

    override fun handleCallback(
        callbackLink: String,
        method: AuthMethod
    ): Flow<CallbackState> {
        throw IllegalStateException("Unsupported operation, this is a demo")
    }

    override fun checkToken(token: String): Flow<TokenInfo> {
        throw IllegalStateException("Unsupported operation, this is a demo")
    }
}