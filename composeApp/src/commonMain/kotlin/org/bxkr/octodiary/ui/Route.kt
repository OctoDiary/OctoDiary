package org.bxkr.octodiary.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun routeFlow(): Flow<NavDestinations> = flow {
    emit(NavDestinations.Home)
}

enum class NavDestinations(val route: String) {
    Diary("diary"),
    Homeworks("homeworks"),
    Home("home"),
    Marks("marks"),
    Profile("profile")
}