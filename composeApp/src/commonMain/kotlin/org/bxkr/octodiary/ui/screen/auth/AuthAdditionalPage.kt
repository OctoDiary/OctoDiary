package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthAdditionalPage(
    viewModel: AuthViewModel = koinViewModel(),
//    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            LargeFlexibleTopAppBar(
                { Text(uiState.selectedDiary?.name ?: "Дневник") },
                subtitle = { Text(when (uiState.additionalPageContent) {
                    AuthViewModel.AdditionalPageContent.TokenPrompt -> "Вход по токену"
                    null -> "Дополнительный шаг"
                }) },
                navigationIcon = {
                    IconButton({ viewModel.goBack() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack, null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            when (uiState.additionalPageContent) {
                AuthViewModel.AdditionalPageContent.TokenPrompt -> AuthTokenPrompt()
                null -> Text("Экран не найден")
            }
        }
    }
}