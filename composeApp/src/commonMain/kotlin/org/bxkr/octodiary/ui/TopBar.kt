package org.bxkr.octodiary.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.app_name
import kmpdiary.composeapp.generated.resources.auth
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OctoDiaryTopBar(isAuthorized: Boolean = true) {
    val title by rememberTitleFlow().collectAsState(Title(Res.string.app_name))
    TopAppBar(
        title = { Text(stringResource(if (isAuthorized) title.stringResource else Res.string.auth)) }
    )
}