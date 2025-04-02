package org.bxkr.octodiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.dashboard
import kmpdiary.composeapp.generated.resources.diary
import kmpdiary.composeapp.generated.resources.homeworks
import kmpdiary.composeapp.generated.resources.marks
import kmpdiary.composeapp.generated.resources.profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.koinInject

@Composable
fun rememberTitleFlow(): Flow<Title> {
    val routeFlow = koinInject<Flow<NavDestinations>>()
    return remember {
        routeFlow.map {
            when (it) {
                NavDestinations.Diary -> Res.string.diary
                NavDestinations.Homeworks -> Res.string.homeworks
                NavDestinations.Home -> Res.string.dashboard
                NavDestinations.Marks -> Res.string.marks
                NavDestinations.Profile -> Res.string.profile
            }
        }.map { Title(it) }
    }
}

class Title(val stringResource: StringResource) {
    override fun toString(): String {
        return stringResource.key
    }
}