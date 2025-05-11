package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.auth_methods
import org.bxkr.octodiary.DeeplinkHolder
import org.bxkr.octodiary.ui.TopBarManager
import org.bxkr.octodiary.ui.resolve
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen() {
    val topBar = koinInject<TopBarManager>()
    val vm = koinViewModel<AuthViewModel>()
    val currentPage by vm.currentPage.collectAsState()
    val methodsTitle = Res.string.auth_methods.resolve()
    val deeplink by koinInject<DeeplinkHolder>().deeplink.collectAsState()
    val hideActions by vm.hideActions.collectAsState()

    LaunchedEffect(currentPage to hideActions) {
        when (currentPage) {
            2 -> topBar.setNavigation {
                IconButton({
                    vm.goBack()
                    vm.showActions()
                }) {
                    Icon(
                        Icons.Rounded.Close,
                        null
                    )
                }
            }

            else -> topBar.clearNavigation()
        }

        if (!hideActions)
            when (currentPage) {
                0 -> topBar.setActions {
                    IconButton({}) {
                        Icon(
                            Icons.Rounded.MoreVert,
                            null
                        )
                    }
                }

                2 -> topBar.setActions {
                    IconButton({ vm.triggerRefresh() }) {
                        Icon(
                            Icons.Rounded.Refresh,
                            null
                        )
                    }
                }

                else -> topBar.clearActions()
            }
        else topBar.clearActions()

        topBar.overrideTitle(
            when (currentPage) {
                1 -> methodsTitle
                else -> null
            }
        )
    }

    LaunchedEffect(deeplink) {
        if (deeplink != null) {
            vm.setPage(2)
        }
    }

    val steps: List<@Composable () -> Unit> =
        listOf(
            { Step1() },
            { Step2() },
            { Step3() }
        )

    val pagerState = rememberPagerState { steps.size }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    HorizontalPager(pagerState, userScrollEnabled = false) { pageIndex ->
        steps[pageIndex].invoke()
    }
}