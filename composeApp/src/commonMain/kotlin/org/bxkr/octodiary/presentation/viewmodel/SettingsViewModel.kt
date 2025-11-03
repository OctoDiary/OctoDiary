package org.bxkr.octodiary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.domain.usecase.auth.LogoutUseCase
import org.bxkr.octodiary.presentation.state.SettingsUiState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SettingsViewModel(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<SettingsUiState>() {
    override val _uiState = MutableStateFlow(SettingsUiState())

    fun goToSubpageSelection() = uu { it.copy(currentSubpage = null) }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}