package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.bxkr.octodiary.getService
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Step3() {
    val vm = koinViewModel<AuthViewModel>()
    vm
        .region
        .collectAsState()
        .value
        .getService()
        .Step3(vm)
}