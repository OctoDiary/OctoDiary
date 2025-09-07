package org.bxkr.octodiary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import org.bxkr.octodiary.AndroidPlatform
import org.bxkr.octodiary.IOSPlatform
import org.bxkr.octodiary.Platform
import org.bxkr.octodiary.domain.model.Diary
import org.bxkr.octodiary.domain.model.Region
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.domain.model.auth.AuthMethodData
import org.bxkr.octodiary.domain.model.auth.AuthStepResult
import org.bxkr.octodiary.domain.model.auth.Credentials
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.usecase.auth.CheckTokenUseCase
import org.bxkr.octodiary.domain.usecase.auth.ExecuteAuthStepUseCase
import org.bxkr.octodiary.domain.usecase.auth.FinishCallbackHandlingUseCase
import org.bxkr.octodiary.domain.usecase.auth.GetAuthMethodsUseCase
import org.bxkr.octodiary.domain.usecase.auth.GetRegionDiariesUseCase
import org.bxkr.octodiary.network.exception.FailedConnectionException
import org.bxkr.octodiary.presentation.state.AuthUiState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AuthViewModel(
    private val executeAuthStepUseCase: ExecuteAuthStepUseCase,
    private val getAuthMethodsUseCase: GetAuthMethodsUseCase,
    private val getRegionDiariesUseCase: GetRegionDiariesUseCase,
    private val finishCallbackHandlingUseCase: FinishCallbackHandlingUseCase,
    private val checkTokenUseCase: CheckTokenUseCase
) : BaseViewModel<AuthUiState>() {
    override val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun goNext() = uu { it.copy(currentPage = it.currentPage + 1) }
    fun goBack() = uu {
        if (it.currentPage > 0) it.copy(currentPage = it.currentPage - 1) else if (it.additionalPageContent != null) it.copy(
            additionalPageContent = null
        ) else it
    }

    fun selectRegion(region: Region, listIndex: Int) = uu {
        it.copy(
            selectedRegion = region,
            initialRegionIndex = listIndex,
            availableDiaries = null
        )
    }

    fun getAvailableDiaries() {
        viewModelScope.launch {
            uu { it.copy(isLoading = true) }
            uu {
                it.copy(
                    availableDiaries = getRegionDiariesUseCase(
                        _uiState.value.selectedRegion
                            ?: throw IllegalStateException("no region selected")
                    )
                )
            }
            uu { it.copy(isLoading = false) }
        }
    }

    fun selectDiary(diary: Diary) = uu {
        it.copy(
            selectedRegion = diary.region,
            selectedDiary = diary,
            authMethods = null
        )
    }

    fun getAuthMethods() {
        viewModelScope.launch {
            uu { it.copy(isLoading = true) }
            uu {
                it.copy(
                    authMethods = getAuthMethodsUseCase(
                        _uiState.value.selectedDiary
                            ?: throw IllegalStateException("no diary selected")
                    )
                )
            }
            uu { it.copy(isLoading = false) }
        }
    }

    fun filterAuthMethods(platform: Platform, authMethods: List<AuthMethod>): List<AuthMethod> =
        authMethods.filter {
            when (platform) {
                is AndroidPlatform -> it !is AuthMethod.WebView
                is IOSPlatform -> it !is AuthMethod.InBrowser
                else -> true
            }
        }

    fun discardError() = uu { it.copy(error = null) }

    fun clickAuthMethod(authMethod: AuthMethod, onFree: () -> Unit) {
        viewModelScope.launch {
            val diary = _uiState.value.selectedDiary
                ?: throw IllegalStateException("no diary selected")
            val result = executeAuthStepUseCase(Credentials.FreshAuth(authMethod), diary)
            when (result) {
                is AuthStepResult.ProceedWithAuthMethod -> proceedWithAuthMethod(
                    authMethod,
                    result.authMethodData,
                    onFree
                )

                is AuthStepResult.Failure -> {
                    uu { it.copy(error = getErrorDescription(result)) }
                    onFree()
                }

                else -> throw IllegalStateException("Credentials/AuthStepResult connection is broken")
            }
        }
    }

    sealed class ErrorDescription(
        open val message: String
    ) {
        data class ConnectionError(
            override val message: String
        ) : ErrorDescription(message)

        data class OtherError(
            override val message: String
        ) : ErrorDescription(message)
    }

    sealed class AdditionalPageContent {
        data object TokenPrompt : AdditionalPageContent()
    }

    private fun getErrorDescription(failure: AuthStepResult.Failure): ErrorDescription =
        when (failure.error) {
            is FailedConnectionException -> ErrorDescription.ConnectionError(failure.message)
            else -> ErrorDescription.OtherError(failure.message)
        }

    private suspend fun proceedWithAuthMethod(
        method: AuthMethod,
        methodData: AuthMethodData,
        onFree: () -> Unit
    ) {
        if (methodData is AuthMethodData.GoToUrl) {
            uu { it.copy(openLink = methodData) }
            onFree()
        } else if (method is AuthMethod.AccessToken && methodData is AuthMethodData.Proceed) {
            uu { it.copy(
                currentPage = 3,
                additionalPageContent = AdditionalPageContent.TokenPrompt
            ) }
            println(_uiState.value.currentPage)
            onFree()
        }
    }

    fun finishCallbackHandling() {
        viewModelScope.launch {
            finishCallbackHandlingUseCase()
        }
    }

    fun checkToken(token: String) {
        val diaryId = _uiState.value.selectedDiary?.id
        if (diaryId == null) return
        uu {
            it.copy(
                tokenInfoFlow =
                    checkTokenUseCase(token, diaryId).stateIn(
                        viewModelScope, SharingStarted.Eagerly, TokenInfo.Loading
                    )
            )
        }
    }
}