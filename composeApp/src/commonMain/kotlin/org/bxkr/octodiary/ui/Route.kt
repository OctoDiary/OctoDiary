package org.bxkr.octodiary.ui

import kotlinx.coroutines.flow.MutableStateFlow

fun routeFlow(): MutableStateFlow<NavDestinations> = MutableStateFlow(NavDestinations.Diary)

enum class NavDestinations(val route: String) {
    Diary("diary"),
    Homeworks("homeworks"),
    Home("home"),
    Marks("marks"),
    Profile("profile")
}