package org.bxkr.octodiary.data.repository

import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.InternalSerializationApi
import org.bxkr.octodiary.data.StorageLatest
import org.bxkr.octodiary.data.exception.callbackfailure.CallbackHandlingFailureException
import org.bxkr.octodiary.data.serialName
import org.bxkr.octodiary.data.toAuthStepFailure
import org.bxkr.octodiary.di.DeeplinkHolder
import org.bxkr.octodiary.di.annotation.MainStorage
import org.bxkr.octodiary.domain.model.auth.AccessCredentials
import org.bxkr.octodiary.domain.model.auth.AuthInfo
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthMethod.AccessToken
import org.bxkr.octodiary.domain.model.auth.AuthMethod.InBrowser
import org.bxkr.octodiary.domain.model.auth.AuthMethod.LoginPassword
import org.bxkr.octodiary.domain.model.auth.AuthMethod.Telegram
import org.bxkr.octodiary.domain.model.auth.AuthMethod.WebView
import org.bxkr.octodiary.domain.model.auth.AuthState
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.CallbackState
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.model.auth.LogoutResult
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.model.diary.Diary
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.region.Region
import org.bxkr.octodiary.domain.model.region.RegionCode
import org.bxkr.octodiary.domain.repository.AuthRepository
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class AuthRepositoryImpl(
    private val authInfo: AuthInfo,
    @param:MainStorage private val kStore: KStore<StorageLatest>,
    private val deeplinkHolder: DeeplinkHolder
) : AuthRepository, KoinComponent {
    override suspend fun normalizeAuthState() {
        val storage = kStore.get()
            ?: throw IllegalStateException("no default KStore found")

        if (storage.callbackAuthState != null) {
            kStore.update { it?.copy(callbackAuthState = null) }
        }
    }

    override fun getAuthStateFlow(): Flow<AuthState> = kStore.updates.map {
        it?.authState ?: AuthState.NotAuthorized
    }

    override suspend fun processAuthStep(
        credentials: Credentials, diarySystem: Diary
    ): AuthStepResult {
        val gateway = authInfo.gateways[diarySystem.id]
        return if (gateway != null) {
            try {
                val authStepResult = gateway.processAuthStep(credentials)
                if (authStepResult is AuthStepResult.Success) {
                    saveAccessCredentials(authStepResult.accessCredentials)
                }

                authStepResult
            } catch (exception: Exception) {
                AuthStepResult.Failure(
                    exception,
                    "Auth error for diary ${diarySystem.id} caught by AuthRepository: ${exception.message}"
                )
            }
        } else {
            IllegalStateException("Unsupported diary system ${diarySystem.id}").toAuthStepFailure()
        }
    }

    override suspend fun logout(): LogoutResult {
        kStore.update {
            it?.copy(accessCredentials = null)
        }
        return LogoutResult.Success
    }

    override suspend fun getRegionDiaries(region: Region): List<Diary> {
        return when (region.regionCode) {
            RegionCode.Moscow.code -> listOf(
                Diary(
                    id = DiaryId.MesMos, name = "МЭШ", region
                ), Diary(
                    id = DiaryId.SpoMos, name = "Колледж МЭШ", region
                ), Diary(
                    id = DiaryId.Demo, name = "Демо :)))", region
                )
            )

            RegionCode.MosReg.code -> listOf(
                Diary(
                    id = DiaryId.MesMosReg, name = "Моя школа", region
                )
            )

            RegionCode.Kaluga.code -> listOf(
                Diary(
                    id = DiaryId.MesKaluga, name = "Моя школа", region
                )
            )

            else -> emptyList()
        }
    }

    override suspend fun getAuthMethods(diary: Diary): List<AuthMethod> {
        return when (diary.id) {
            DiaryId.MesMos -> listOf(
                InBrowser.MosRu, WebView.MosRu, Telegram, AccessToken(listOf(
                    AccessToken.TokenFormat.School, AccessToken.TokenFormat.Uchebnik
                ))
            )

            DiaryId.SpoMos -> listOf(
                InBrowser.MosRu, WebView.MosRu, AccessToken()
            )

            DiaryId.MesMosReg -> listOf(
                InBrowser.Esia, WebView.Esia, Telegram, LoginPassword, AccessToken(listOf(
                    AccessToken.TokenFormat.School, AccessToken.TokenFormat.Uchebnik
                ))
            )

            DiaryId.Demo -> listOf(AuthMethod.Demo)

            else -> emptyList()
        }
    }

    private suspend fun saveAccessCredentials(accessCredentials: AccessCredentials) =
        kStore.update {
            it?.copy(
                accessCredentials = accessCredentials
            )
        }

    override suspend fun startCollectingDeeplink() = deeplinkHolder.deeplink.onEach { deeplink ->
        val authState = kStore.get()?.authState
        if (deeplink != null && authState is AuthState.Callback.WaitingForCallback) {
            catchCallbackDeeplink(deeplink, authState)
        }
    }.collect()

    @OptIn(InternalSerializationApi::class)
    private suspend fun catchCallbackDeeplink(
        callbackLink: String, authState: AuthState.Callback.WaitingForCallback
    ) {
        val gateway = authInfo.gateways[authState.diaryId]
        if (gateway != null) {
            kStore.update {
                it?.copy(
                    callbackAuthState = AuthState.Callback.HandlingCallback(CallbackState.Initialized)
                )
            }
            gateway.handleCallback(callbackLink, authState.authMethod).onEach { callbackState ->
                    kStore.update {
                        it?.copy(
                            callbackAuthState = AuthState.Callback.HandlingCallback(callbackState)
                        )
                    }
                }.catch { throwable ->
                    if (throwable !is CallbackHandlingFailureException) throw throwable
                    kStore.update {
                        it?.copy(
                            callbackAuthState = AuthState.Callback.FailedToHandle(
                                "diaryId: ${authState.diaryId::class.serialName}; " +
                                        "authMethod: ${authState.authMethod::class.serialName}",
                                throwable.domainError
                            )
                        )
                    }
                }.collect()
        }
    }

    override suspend fun finishCallbackHandling() =
        kStore.update {
            it?.copy(callbackAuthState = null)
        }

    override fun checkToken(token: String, diaryId: DiaryId): Flow<TokenInfo> {
        val gateway = authInfo.gateways[diaryId]
        return gateway?.checkToken(token)
            ?: throw IllegalStateException("Gateway not found")
    }
}