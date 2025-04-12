package org.bxkr.octodiary.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.app_name
import kmpdiary.composeapp.generated.resources.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OctoDiaryTopBar(isAuthorized: Boolean = true, topBarManager: TopBarManager = koinInject()) {
    val actions by topBarManager.actions.collectAsState()
    val title by rememberTitleFlow().collectAsState(Title(Res.string.app_name))
    TopAppBar(
        title = {
            AnimatedContent(
                isAuthorized,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }
            ) {
                Text((if (it) title.stringResource else Res.string.auth).resolve())
            }
        },
        actions = {
            AnimatedContent(
                actions,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }
            ) {
                it.invoke()
            }
        }
    )
}

class TopBarManager {
    private val _actions: MutableStateFlow<@Composable () -> Unit> =
        MutableStateFlow {}

    val actions = _actions.asStateFlow()

    fun setActions(content: @Composable () -> Unit) {
        _actions.value = content
    }

    fun clearActions() {
        _actions.value = {}
    }
}