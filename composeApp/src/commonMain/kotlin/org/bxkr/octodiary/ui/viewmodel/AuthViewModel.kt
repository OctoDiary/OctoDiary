package org.bxkr.octodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.bxkr.octodiary.Region
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.network.apis.MosAuthClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {
    private val mosAuthClient: MosAuthClient by inject()
    private val _region: MutableStateFlow<Region> = MutableStateFlow(Region.Moscow)
    private val _currentPage: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _isConnected: MutableStateFlow<Result<Boolean>?> = MutableStateFlow(null)
    private val _needToRefresh: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _hideActions: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val region = _region.asStateFlow()
    val currentPage = _currentPage.asStateFlow()
    val isConnected = _isConnected.asStateFlow().map { it ?: Result.Loading }
    val needToRefresh = _needToRefresh.asStateFlow()
    val hideActions = _hideActions.asStateFlow()


    fun checkConnection() {
        if (_isConnected.value == null) viewModelScope.launch {
            _isConnected.value = Result.Loading
            _isConnected.value = Result.Success(mosAuthClient.checkConnection())
        }
    }

    fun setRegion(newRegion: Region) {
        _region.value = newRegion
    }

    fun goNext() {
        _currentPage.value += 1
    }

    fun goBack() {
        _currentPage.value -= 1
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }

    fun triggerRefresh() = _needToRefresh.run { value = !value }

    fun authFinished() {
        _region.value = Region.Moscow
        _currentPage.value = 0
        _isConnected.value = null
        _needToRefresh.value = false
        _hideActions.value = false
    }

    fun hideActions() {
        _hideActions.value = true
    }

    fun showActions() {
        _hideActions.value = false
    }
}