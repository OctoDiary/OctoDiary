package org.bxkr.octodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.ResultFlow
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {
    private val mosAuthClient: MosAuthClient by inject()

    fun checkConnection(): ResultFlow<Boolean> =
        MutableStateFlow<Result<Boolean>>(Result.Loading).apply {
            viewModelScope.launch {
                emit(Result.Success(mosAuthClient.checkConnection()))
            }
        }.asStateFlow()
}