package org.bxkr.octodiary.ui.screen.diary.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.profile
import org.bxkr.octodiary.domain.model.user.UserProfile
import org.bxkr.octodiary.presentation.viewmodel.diary.ProfileViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFadeNotNull
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.needToLoad) {
        if (uiState.needToLoad) {
            viewModel.loadProfile()
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibilityFade(uiState.isLoading) { LoadingIndicator() }
        AnimatedVisibilityFadeNotNull(uiState.profile) { profile -> ProfileDescription(profile) }
    }
}

@Composable
fun ProfileTitle() {
    Text(stringResource(Res.string.profile))
}

@Composable
private fun ProfileDescription(profile: UserProfile) {
    Column(Modifier.fillMaxSize()) {
        Text(profile.fullName)
        Text(profile.students.first().studentId)
    }
}