package org.bxkr.octodiary

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bxkr.octodiary.screens.HomeScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        val navController = rememberNavController()
        OctoDiaryTheme {
            NavHost(
                navController = navController,
                startDestination = NavDestinations.Home.route
            ) {
                composable(NavDestinations.Home.route) {
                    HomeScreen()
                }
            }
        }
    }
}

enum class NavDestinations(val route: String) {
    Home("home")
}