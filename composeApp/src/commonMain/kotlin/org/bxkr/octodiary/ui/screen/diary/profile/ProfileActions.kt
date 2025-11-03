package org.bxkr.octodiary.ui.screen.diary.profile

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.settings
import org.bxkr.octodiary.presentation.viewmodel.NavViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RowScope.ProfileActions(
    navViewModel: NavViewModel = koinViewModel()
) {
    IconButton(
        { navViewModel.openSettings() }
    ) {
        Icon(
            Icons.Rounded.Settings,
            stringResource(Res.string.settings)
        )
    }
}