package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
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
                    is AuthViewModel.AdditionalPageContent.TokenPrompt -> "Вход по токену"
                    is AuthViewModel.AdditionalPageContent.WebView -> "Авторизация"
                    else -> "Дополнительный шаг"
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
            when (val content = uiState.additionalPageContent) {
                is AuthViewModel.AdditionalPageContent.TokenPrompt -> AuthTokenPrompt()
                is AuthViewModel.AdditionalPageContent.WebView -> AuthWebView(content)
                else -> Text("Экран не найден")
            }
        }
    }
}