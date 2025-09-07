package org.bxkr.octodiary.presentation.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.bxkr.octodiary.domain.usecase.auth.GetAuthStateFlowUseCase
import org.bxkr.octodiary.domain.usecase.auth.StartCollectingDeeplinkUseCase
import org.bxkr.octodiary.presentation.state.MainUiState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val startCollectingDeeplinkUseCase: StartCollectingDeeplinkUseCase
) : BaseViewModel<MainUiState>() {
    override val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
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