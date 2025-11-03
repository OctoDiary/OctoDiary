package org.bxkr.octodiary.ui.screen.diary.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.dashboard
import org.bxkr.octodiary.presentation.viewmodel.NavViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    // homeViewModel: HomeViewModel = koinViewModel()
    navViewModel: NavViewModel
) {
    Column {
        Text("welcome")

        OutlinedButton({
            navViewModel.goToProfile()
        }) {
            Text("go to ProfileScreen")
        }
    }
}

@Composable
fun HomeTitle() {
    Text(stringResource(Res.string.dashboard))
}