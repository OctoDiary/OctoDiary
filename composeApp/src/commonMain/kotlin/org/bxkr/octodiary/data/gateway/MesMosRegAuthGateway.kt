package org.bxkr.octodiary.data.gateway

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.exception.callbackfailure.CallbackHandlingFailureException
import org.bxkr.octodiary.data.exception.callbackfailure.InvalidAuthMethodError
import org.bxkr.octodiary.data.model.auth.AuthEduInfo
import org.bxkr.octodiary.data.toAuthStepFailure
import org.bxkr.octodiary.di.annotation.MainStorage
import org.bxkr.octodiary.domain.ExternalIntegration
import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.auth.*
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.region.RegionCode
import org.koin.core.annotation.Single
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Single
class MesMosRegAuthGateway(
    @param:MainStorage private val kStore: KStore<StorageLatest>
) : AuthGateway {
    override val responsibleFor: DiaryId
        get() = DiaryId.MesMosReg

    private object AuthEduConstants {
        const val PASSWORD_LOGIN_URL_TEMPLATE =
            "https://authedu.mosreg.ru/v3/auth/kauth/login?redirect_url=dnevnik-myschool://authRegionRedirect&state="
        const val REDIRECT_URI_START = "dnevnik-myschool://authRegionRedirect"
    }

    override suspend fun processAuthStep(credentials: Credentials): AuthStepResult = when (credentials) {
        is Credentials.FreshAuth -> initializeAuth(credentials.authMethod)
        is Credentials.AccessToken -> TODO("Not yet implemented")
    }

    override fun handleCallback(
        callbackLink: String,
        method: AuthMethod
    ): Flow<CallbackState> = flow {
        emit(CallbackState.Loading)
        when (method) {
            AuthMethod.LoginPassword -> handlePasswordCallback(callbackLink)
            else -> throw CallbackHandlingFailureException(InvalidAuthMethodError())
        }
    }

    override fun checkToken(token: String): Flow<TokenInfo> {
        TODO("Not yet implemented")
    }

    private suspend fun initializeAuth(authMethod: AuthMethod): AuthStepResult =
        when (authMethod) {
            is AuthMethod.Telegram -> proceedWithTelegram()
            is AuthMethod.LoginPassword -> proceedWithPassword()
            is AuthMethod.AccessToken -> AuthStepResult.ProceedWithAuthMethod(AuthMethodData.Proceed)
            else -> IllegalStateException("UnsupportedAuthMethod").toAuthStepFailure()
        }

    private fun proceedWithTelegram() = AuthStepResult.ProceedWithAuthMethod(
        AuthMethodData.GoToUrl(
            ExternalIntegration.getTelegramAuthLink(RegionCode.MosReg.code),
            isWebView = false
        )
    )

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun proceedWithPassword(): AuthStepResult {
        val state = Uuid.random().toString()
        kStore.update {
            it?.copy(
                authGatewayStorage = AuthGatewayStorage.MesMosReg(
                    AuthEduInfo(state)
                ),
                callbackAuthState = AuthState.Callback.WaitingForCallback(
                    authMethod = AuthMethod.LoginPassword,
                    diaryId = DiaryId.MesMosReg
                )
            )
        }

        return AuthStepResult.ProceedWithAuthMethod(
            AuthMethodData.GoToUrl(
                AuthEduConstants.PASSWORD_LOGIN_URL_TEMPLATE + state,
                isWebView = true,
                webViewListener = { url -> url.startsWith(AuthEduConstants.REDIRECT_URI_START) }
            )
        )
    }

    private suspend fun handlePasswordCallback(callbackLink: String) {

    }
}