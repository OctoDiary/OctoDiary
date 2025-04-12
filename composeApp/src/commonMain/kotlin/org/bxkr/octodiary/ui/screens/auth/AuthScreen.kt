package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import org.bxkr.octodiary.ui.TopBarManager
import org.koin.compose.koinInject

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

    val steps: List<@Composable (suspend () -> Unit) -> Unit> = listOf(
        { Step1(it) },
        { Step2() }
    )

    val pagerState = rememberPagerState { steps.size }

    HorizontalPager(pagerState, userScrollEnabled = false) { pageIndex ->
        steps[pageIndex].invoke { pagerState.animateScrollToPage(pageIndex + 1) }
    }
}