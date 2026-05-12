package org.bxkr.octodiary.data.gateway

import io.github.xxfast.kstore.KStore
import io.ktor.http.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import okio.ByteString.Companion.toByteString
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.datasource.remote.MesMosRemoteDataSource
import org.bxkr.octodiary.data.exception.callbackfailure.*
import org.bxkr.octodiary.data.model.api.mes.auth.IssueCallResponse
import org.bxkr.octodiary.data.model.api.mes.profile.Profile
import org.bxkr.octodiary.data.model.auth.MosRuInfo
import org.bxkr.octodiary.data.model.auth.accesscredentials.token.*
import org.bxkr.octodiary.data.toAuthStepFailure
import org.bxkr.octodiary.di.annotation.MainStorage
import org.bxkr.octodiary.domain.ExternalIntegration
import org.bxkr.octodiary.domain.gateway.AuthGateway
import org.bxkr.octodiary.domain.model.auth.*
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.region.RegionCode
import org.bxkr.octodiary.domain.model.user.UserType
import org.koin.core.annotation.Single
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Single
class MesMosAuthGateway(
    @param:MainStorage private val kStore: KStore<StorageLatest>,
    private val mesMosRemoteDataSource: MesMosRemoteDataSource
) : AuthGateway {
    override val responsibleFor: DiaryId
        get() = DiaryId.MesMos

    private object MosRuConstants {
        const val MOS_AUTH_GATE_URL = "https://login.mos.ru/sps/oauth/ae"
        const val SCOPE =
            "birthday contacts openid profile snils blitz_change_password blitz_user_rights blitz_qr_auth"
        const val RESPONSE_TYPE = "code"
        const val PROMPT = "login"
        const val BIP_ACTION_HINT = "used_sms"
        const val REDIRECT_URI = "dnevnik-mes://oauth2redirect"
        const val ACCESS_TYPE = "offline"
        const val CODE_CHALLENGE_METHOD = "S256"
    }

    private object DeeplinkConstants {
        const val MOS_SCHEME = "dnevnik-mes"
        const val MOS_HOST = "oauth2redirect"
        const val MOS_CODE_PARAMETER_NAME = "code"
        const val OCTODIARY_SCHEME = "octodiary"
        const val TELEGRAM_HOST = "tgbot"
    }

    private suspend fun getGatewayStorage(): AuthGatewayStorage.MesMos? =
        kStore.get()?.authGatewayStorage as? AuthGatewayStorage.MesMos


    override suspend fun processAuthStep(credentials: Credentials): AuthStepResult =
        when (credentials) {
            is Credentials.FreshAuth -> initializeAuth(credentials.authMethod)
            is Credentials.AccessToken -> authorizeByToken(credentials.accessToken)
        }

    private fun getUrl(credentials: IssueCallResponse, codeVerifier: String): String =
        with(MosRuConstants) {
            val codeChallenge = encodeToBase64(hash(codeVerifier))
            val uri = URLBuilder(Url(MOS_AUTH_GATE_URL))
            uri.parameters.apply {
                append("scope", SCOPE)
                append("access_type", ACCESS_TYPE)
                append("response_type", RESPONSE_TYPE)
                append("client_id", credentials.clientId)
                append("redirect_uri", REDIRECT_URI)
                append("prompt", PROMPT)
                append("code_challenge", codeChallenge)
                append("code_challenge_method", CODE_CHALLENGE_METHOD)
                append("bip_action_hint", BIP_ACTION_HINT)
            }
            uri.build().toString()
        }

    private fun getRandomString(length: Int = 80): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '_' + '-'
        return (1..length).map { allowedChars.random() }.joinToString("")
    }

    private fun hash(string: String): ByteArray {
        val bytes = string.toByteArray().toByteString()
        return bytes.sha256().toByteArray()
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeToBase64(byteArray: ByteArray): String {
        return Base64.UrlSafe.encode(byteArray).replace("=", "")
    }

    private suspend fun initializeAuth(authMethod: AuthMethod): AuthStepResult = when (authMethod) {
        is AuthMethod.InBrowser.MosRu -> initializeMosRuAuth(authMethod)
        is AuthMethod.WebView.MosRu -> initializeMosRuAuth(authMethod)
        is AuthMethod.Telegram -> proceedWithTelegramAuth()
        is AuthMethod.AccessToken -> AuthStepResult.ProceedWithAuthMethod(AuthMethodData.Proceed)
        else -> IllegalStateException("UnsupportedAuthMethod").toAuthStepFailure()
    }

    private suspend fun initializeMosRuAuth(method: AuthMethod): AuthStepResult {
        val isInBrowser = method is AuthMethod.InBrowser.MosRu
        val credentialsResult = mesMosRemoteDataSource.mosRuIssueCall()
        val credentials =
            credentialsResult.getOrNull() ?: return credentialsResult.toAuthStepFailure()

        val codeVerifier = getRandomString()

        kStore.update {
            it?.copy(
                authGatewayStorage = AuthGatewayStorage.MesMos(
                    mosRuInfo = MosRuInfo(
                        credentials.clientId, credentials.clientSecret, codeVerifier
                    )
                ), callbackAuthState = AuthState.Callback.WaitingForCallback(
                    method, DiaryId.MesMos
                )
            )
        }

        return AuthStepResult.ProceedWithAuthMethod(
            AuthMethodData.GoToUrl(
                getUrl(
                    credentials, codeVerifier
                ), !isInBrowser
            )
        )
    }

    private fun proceedWithTelegramAuth() = AuthStepResult.ProceedWithAuthMethod(
        AuthMethodData.GoToUrl(
            ExternalIntegration.getTelegramAuthLink(RegionCode.Moscow.code), false
        )
    )

    private fun authorizeByToken(accessToken: String): AuthStepResult {
        val mesToken = MesToken(accessToken)
        return if (mesToken.payload != null) {
            AuthStepResult.Success(
                AccessCredentials.MesMosAccessCredentials(mesToken, null, null)
            )
        } else IllegalStateException("Invalid MES JWT token").toAuthStepFailure()
    }

    override fun handleCallback(
        callbackLink: String, method: AuthMethod
    ): Flow<CallbackState> = flow {
        emit(CallbackState.Loading)
        when (method) {
            AuthMethod.InBrowser.MosRu, AuthMethod.WebView.MosRu -> handleMosRuCallback(callbackLink)
            AuthMethod.Telegram -> handleTelegramCallback(callbackLink)
            else -> throw CallbackHandlingFailureException(InvalidAuthMethodError())
        }
    }

    private suspend fun handleMosRuCallback(callbackLink: String) = try {
        val url = URLBuilder(callbackLink)
        if (url.protocol.name != DeeplinkConstants.MOS_SCHEME || url.host != DeeplinkConstants.MOS_HOST) throw CallbackHandlingFailureException(
            InvalidLinkFormatError()
        )
        val code = url.parameters[DeeplinkConstants.MOS_CODE_PARAMETER_NAME]
            ?: throw CallbackHandlingFailureException(InvalidLinkFormatError())
        val mosRuInfo = getGatewayStorage()?.mosRuInfo ?: throw CallbackHandlingFailureException(
            AuthGatewayDataNotFoundError()
        )
        handleMosRuCode(code, mosRuInfo)
    } catch (_: URLParserException) {
        throw CallbackHandlingFailureException(InvalidLinkFormatError())
    }

    private suspend fun handleTelegramCallback(callbackLink: String) {
        TODO()
    }

    private suspend fun handleMosRuCode(
        code: String, mosRuInfo: MosRuInfo
    ) {
        val handleCodeResult = mesMosRemoteDataSource.handleCode(code, mosRuInfo)
        val tokenExchange = handleCodeResult.getOrElse {
            throw CallbackHandlingFailureException(
                CodeHandlingError(it.message, it.stackTraceToString())
            )
        }
        val mosToMesResult = mesMosRemoteDataSource.mosToMes(tokenExchange.accessToken)
        val mesToken = mosToMesResult.getOrElse {
            throw CallbackHandlingFailureException(
                CodeHandlingError(it.message, it.stackTraceToString())
            )
        }
        kStore.update {
            it?.copy(
                accessCredentials = AccessCredentials.MesMosAccessCredentials(
                    mesToken, mosRuInfo, tokenExchange.refreshToken
                ),
                callbackAuthState = null
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    override fun checkToken(token: String): Flow<TokenInfo> = flow<TokenInfo> {
        val jwtPayload =
            token.jwtPayloadTyped<MesPayload>() ?: token.jwtPayloadTyped<UchebnikPayload>()
        if (jwtPayload == null) return@flow emit(TokenInfo.Unsupported)
        if (jwtPayload is UchebnikPayload) {
            val uchebnikStandard = TokenInfo.Standard(
                furtherLoading = true,
                actualDiaryName = "Библиотека МЭШ",
                expiryTime = Instant.fromEpochSeconds(jwtPayload.expiryDate),
                issueTime = Instant.fromEpochSeconds(jwtPayload.issuedAt),
                userId = jwtPayload.mesPersonId
            )
            emit(uchebnikStandard)
            val mesToken = mesMosRemoteDataSource.toSchoolToken(UchebnikToken(token)).getOrElse {
                return@flow emit(
                    uchebnikStandard.copy(
                        furtherLoading = false, canLogIn = false
                    )
                )
            }
            checkMesToken(mesToken, "Библиотека МЭШ")
        } else if (jwtPayload is MesPayload) {
            checkMesToken(MesToken(token))
        }
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun FlowCollector<TokenInfo>.checkMesToken(
        token: MesToken, originalDiaryName: String? = null
    ) {
        val standard = TokenInfo.Standard(
            furtherLoading = true,
            actualDiaryName = originalDiaryName ?: "МЭШ Москва",
            expiryTime = token.expirationDate,
            issueTime = token.issuedAt,
            userId = token.personId
        )
        emit(standard)
        val profile = mesMosRemoteDataSource.getProfile(token).getOrElse {
            return emit(
                standard.copy(
                    furtherLoading = false, cannotLoadFurther = true, canLogIn = false
                )
            )
        }
        val studentName =
            if (profile.profile.role == Profile.UserType.Student) profile.profile.fullName
            else profile.children.firstOrNull()?.fullName
        emit(
            TokenInfo.Extended(
                standard.copy(
                    furtherLoading = false, canLogIn = true
                ),
                userName = profile.profile.fullName,
                studentName = studentName,
                role = when (profile.profile.role) {
                    Profile.UserType.Student -> UserType.Student
                    Profile.UserType.Parent -> UserType.Parent
                    else -> null
                },
                schoolName = profile.children.firstOrNull()?.school?.name
            )
        )
    }
}