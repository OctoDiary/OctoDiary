package org.bxkr.octodiary.data.region

import androidx.compose.runtime.Composable
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel

interface RegionService {
    @Composable
    fun Step2(vm: AuthViewModel)

    @Composable
    fun Step3(vm: AuthViewModel)
}