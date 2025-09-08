package org.bxkr.octodiary.domain.gateway

import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.domain.model.DiaryId
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.CallbackState
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.model.auth.TokenInfo

interface AuthGateway {
    val responsibleFor: DiaryId

    suspend fun processAuthStep(credentials: Credentials): AuthStepResult

    fun handleCallback(callbackLink: String, method: AuthMethod): Flow<CallbackState>

    fun checkToken(token: String): Flow<TokenInfo>
}