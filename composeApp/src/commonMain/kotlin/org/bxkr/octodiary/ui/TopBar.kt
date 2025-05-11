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
    val navigation by topBarManager.navigation.collectAsState()
    val actions by topBarManager.actions.collectAsState()
    val title by rememberTitleFlow().collectAsState(Title(Res.string.app_name))
    val customTitle by topBarManager.customTitle.collectAsState()
    TopAppBar(
        title = {
            AnimatedContent(
                isAuthorized to customTitle,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }
            ) {
                (if (it.second == null) {
                    (if (it.first) title.stringResource else Res.string.auth).resolve()
                } else it.second)?.let { it1 -> Text(it1) }
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
        },
        navigationIcon = {
            AnimatedContent(
                navigation,
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
    private val _navigation: MutableStateFlow<@Composable () -> Unit> = MutableStateFlow {}
    private val _actions: MutableStateFlow<@Composable () -> Unit> =
        MutableStateFlow {}
    private val _customTitle: MutableStateFlow<String?> = MutableStateFlow(null)

    val navigation = _navigation.asStateFlow()
    val actions = _actions.asStateFlow()
    val customTitle = _customTitle.asStateFlow()

    fun setNavigation(content: @Composable () -> Unit) {
        _navigation.value = content
    }

    fun clearNavigation() {
        _navigation.value = {}
    }

    fun setActions(content: @Composable () -> Unit) {
        _actions.value = content
    }

    fun clearActions() {
        _actions.value = {}
    }

    fun overrideTitle(title: String? = null) {
        _customTitle.value = title
    }
}