package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.bxkr.octodiary.ui.TopBarManager
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen() {
    koinInject<TopBarManager>().setActions {
        IconButton({}) {
            Icon(
                Icons.Rounded.MoreVert,
                null
            )
        }
    }
    val vm = koinViewModel<AuthViewModel>()
    val currentPage by vm.currentPage.collectAsState()

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