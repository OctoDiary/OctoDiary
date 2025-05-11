package org.bxkr.octodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.auth.AuthManager
import org.bxkr.octodiary.data.auth.token.MesToken
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val authManager: AuthManager by inject()

    private val _token: MutableStateFlow<MesToken?> = MutableStateFlow(null)

    fun getToken(): StateFlow<MesToken?> {
        if (_token.value == null) {
            viewModelScope.launch {
                _token.value = authManager.getAccessToken()
            }
        }
        return _token.asStateFlow()
    }

    fun logOut() = viewModelScope.launch { authManager.logOut("button click") }
}