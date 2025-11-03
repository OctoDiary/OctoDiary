package org.bxkr.octodiary.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.presentation.state.NavUiState
import org.bxkr.octodiary.ui.screen.diary.NavDestination
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NavViewModel : BaseViewModel<NavUiState>() {
    override val _uiState: MutableStateFlow<NavUiState> = MutableStateFlow(NavUiState())
    private val _navigationEvents = MutableSharedFlow<NavDestination>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun openSettings() = uu { it.copy(isSettingsPageOpened = true) }

    fun closeSettings() = uu { it.copy(isSettingsPageOpened = false) }

    fun goToProfile() {
        viewModelScope.launch {
            _navigationEvents.emit(NavDestination.Profile)
        }
    }
}