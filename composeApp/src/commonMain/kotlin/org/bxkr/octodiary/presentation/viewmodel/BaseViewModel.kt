package org.bxkr.octodiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<UiState> : ViewModel() {
    @Suppress("PropertyName")
    protected abstract val _uiState: MutableStateFlow<UiState>

    val uiState get() = _uiState.asStateFlow()
    /** *U*iState *U*pdate */
    protected inline fun uu(function: (UiState) -> UiState) = _uiState.update(function)
}