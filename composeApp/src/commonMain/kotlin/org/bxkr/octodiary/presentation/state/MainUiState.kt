package org.bxkr.octodiary.presentation.state

import org.bxkr.octodiary.domain.model.auth.AuthState

data class MainUiState(
    val isDebugMode: Boolean = false,
    val isDebugMenuOpened: Boolean = false,
    val authState: AuthState? = null
)
