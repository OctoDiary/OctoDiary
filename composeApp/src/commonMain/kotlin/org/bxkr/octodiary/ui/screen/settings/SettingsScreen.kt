package org.bxkr.octodiary.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.close
import octodiary4.composeapp.generated.resources.settings
import org.bxkr.octodiary.presentation.viewmodel.NavViewModel
import org.bxkr.octodiary.presentation.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(), navViewModel: NavViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // TODO: Implement NavigationEventHandler, whatever it is
//    PredictiveBackHandler {
//        if (uiState.currentSubpage != null) viewModel.goToSubpageSelection() else navViewModel.closeSettings()
//    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(stringResource(Res.string.settings)) }, navigationIcon = {
                IconButton({ navViewModel.closeSettings() }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(Res.string.close))
                }
            })
        },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Text("settings, hello!")
            OutlinedButton({ viewModel.logout() }) {
                Text("logout")
            }
        }
    }
}