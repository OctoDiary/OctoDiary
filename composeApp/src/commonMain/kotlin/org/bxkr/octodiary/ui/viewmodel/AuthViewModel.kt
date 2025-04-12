package org.bxkr.octodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.Region
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.ResultFlow
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {
    private val mosAuthClient: MosAuthClient by inject()
    private val _region: MutableStateFlow<Region> = MutableStateFlow(Region.Moscow)
    private val _currentPage: MutableStateFlow<Int> = MutableStateFlow(0)

    val region = _region.asStateFlow()
    val currentPage = _currentPage.asStateFlow()


    fun checkConnection(): ResultFlow<Boolean> =
        MutableStateFlow<Result<Boolean>>(Result.Loading).apply {
            viewModelScope.launch {
                emit(Result.Success(mosAuthClient.checkConnection()))
            }
        }.asStateFlow()

    fun setRegion(newRegion: Region) {
        _region.value = newRegion
    }

    fun goNext() {
        _currentPage.value += 1
    }

    fun goBack() {
        _currentPage.value -= 1
    }
}