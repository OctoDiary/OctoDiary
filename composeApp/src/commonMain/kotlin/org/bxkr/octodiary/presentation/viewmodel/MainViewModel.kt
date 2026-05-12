package org.bxkr.octodiary.presentation.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.bxkr.octodiary.domain.model.diary.DiaryCapabilities
import org.bxkr.octodiary.domain.repository.CapabilitiesProvider
import org.bxkr.octodiary.domain.repository.SessionRepository
import org.bxkr.octodiary.domain.usecase.auth.GetAuthStateFlowUseCase
import org.bxkr.octodiary.domain.usecase.auth.NormalizeAppStateUseCase
import org.bxkr.octodiary.domain.usecase.auth.StartCollectingDeeplinkUseCase
import org.bxkr.octodiary.presentation.state.MainUiState
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val startCollectingDeeplinkUseCase: StartCollectingDeeplinkUseCase,
    private val normalizeAppStateUseCase: NormalizeAppStateUseCase,
    private val capabilitiesProvider: CapabilitiesProvider,
    sessionRepository: SessionRepository,
) : BaseViewModel<MainUiState>() {
    override val _uiState = MutableStateFlow(MainUiState())

    val capabilities = sessionRepository.getSessionFlow().mapNotNull {
        it?.diarySystemId?.let { diarySystemId ->
            capabilitiesProvider.getCapabilities(diarySystemId)
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = DiaryCapabilities()
    )

    init {
        viewModelScope.launch {
            normalizeAppStateUseCase()
        }
        viewModelScope.launch {
            getAuthStateFlowUseCase().onEach { authState ->
                uu { it.copy(authState = authState) }
            }.collect()
        }
        viewModelScope.launch {
            startCollectingDeeplinkUseCase()
        }
    }

    val snackbarHostState = SnackbarHostState()

    fun openDebugMenu() = uu { it.copy(isDebugMenuOpened = true) }
    fun closeDebugMenu() = uu { it.copy(isDebugMenuOpened = false) }
    fun enableDebugMode() = uu { it.copy(isDebugMode = true) }
}