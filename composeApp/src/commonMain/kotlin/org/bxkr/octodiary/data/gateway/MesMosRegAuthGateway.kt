package org.bxkr.octodiary.data.gateway

import io.github.xxfast.kstore.KStore
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.datasource.remote.MesMosRegRemoteDataSource
import org.bxkr.octodiary.data.exception.callbackfailure.*
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
    @param:MainStorage private val kStore: KStore<StorageLatest>,
    private val mesMosRegRemoteDataSource: MesMosRegRemoteDataSource,
) : AuthGateway {
    override val responsibleFor: DiaryId
        get() = DiaryId.MesMosReg

    private object AuthEduConstants {
        const val PASSWORD_LOGIN_URL_TEMPLATE =
            "https://authedu.mosreg.ru/v3/auth/kauth/login?redirect_url=dnevnik-myschool://authRegionRedirect&state="
        const val ESIA_URL_TEMPLATE =
            "https://authedu.mosreg.ru/v3/auth/esia/login?redirect_url=dnevnik-myschool://authRegionRedirect&state="
        const val REDIRECT_URI_START = "dnevnik-myschool://authRegionRedirect"
    }

    private object DeeplinkConstants {
        const val MYSCHOOL_SCHEME = "dnevnik-myschool"
        const val MYSCHOOL_HOST = "authRegionRedirect"
        const val CODE_PARAMETER_NAME = "code"
    }

    private suspend fun getGatewayStorage(): AuthGatewayStorage.MesMosReg? =
        kStore.get()?.authGatewayStorage as? AuthGatewayStorage.MesMosReg

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
            AuthMethod.InBrowser.Esia,
            AuthMethod.WebView.Esia,
            AuthMethod.LoginPassword -> handleCallback(callbackLink)
            else -> throw CallbackHandlingFailureException(InvalidAuthMethodError())
        }
    }

    override fun checkToken(token: String): Flow<TokenInfo> {
        TODO("Not yet implemented")
    }

    private suspend fun initializeAuth(authMethod: AuthMethod): AuthStepResult =
        when (authMethod) {
            is AuthMethod.WebView.Esia,
            is AuthMethod.InBrowser.Esia -> initializeEsiaAuth(authMethod)

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
    private suspend fun initializeEsiaAuth(method: AuthMethod): AuthStepResult {
        val isWebView = method is AuthMethod.WebView.Esia
        val state = Uuid.random().toString()
        kStore.update {
            it?.copy(
                authGatewayStorage = AuthGatewayStorage.MesMosReg(
                    AuthEduInfo(state)
                ),
                callbackAuthState = AuthState.Callback.WaitingForCallback(
                    authMethod = method,
                    diaryId = DiaryId.MesMosReg
                )
            )
        }

        return AuthStepResult.ProceedWithAuthMethod(
            AuthMethodData.GoToUrl(
                AuthEduConstants.ESIA_URL_TEMPLATE + state,
                isWebView
            )
        )
    }

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

    private suspend fun handleCallback(callbackLink: String) = try {
        val url = URLBuilder(callbackLink)
        if (url.protocol.name != DeeplinkConstants.MYSCHOOL_SCHEME || url.host != DeeplinkConstants.MYSCHOOL_HOST) throw CallbackHandlingFailureException(
            InvalidLinkFormatError()
        )
        val code = url.parameters[DeeplinkConstants.CODE_PARAMETER_NAME]
            ?: throw CallbackHandlingFailureException(InvalidLinkFormatError())
        val authEduInfo = getGatewayStorage()?.authEduInfo
            ?: throw CallbackHandlingFailureException(AuthGatewayDataNotFoundError())
        handleCode(code, authEduInfo)
    } catch (_: URLParserException) {
        throw CallbackHandlingFailureException(InvalidAuthMethodError())
    }

    private suspend fun handleCode(code: String, authEduInfo: AuthEduInfo) {
        val handleCodeResult = mesMosRegRemoteDataSource.codeToToken(code, authEduInfo.state)
        val regionalTokens = handleCodeResult.getOrElse {
            throw CallbackHandlingFailureException(
                CodeHandlingError(it.message, it.stackTraceToString())
            )
        }
        kStore.update {
            it?.copy(
                accessCredentials = AccessCredentials.MesMosRegAccessCredentials(
                    accessToken = regionalTokens.accessToken,
                    authEduRefreshToken = regionalTokens.refreshToken
                ),
                callbackAuthState = null
            )
        }
    }
}