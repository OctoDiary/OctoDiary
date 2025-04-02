package org.bxkr.octodiary

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.auth.PreAuthManager
import org.bxkr.octodiary.ui.NavDestinations
import org.bxkr.octodiary.ui.OctoDiaryTopBar
import org.bxkr.octodiary.ui.collectResult
import org.bxkr.octodiary.ui.screens.AuthScreen
import org.bxkr.octodiary.ui.screens.HomeScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        val navController = rememberNavController()
        val isAuthorized by PreAuthManager.isAuthorized().collectResult()
        OctoDiaryTheme {
            Scaffold(
                topBar = { OctoDiaryTopBar() }
            ) { paddingValues ->
                AnimatedContent(isAuthorized, Modifier.padding(paddingValues)) {
                    if (it is Result.Success) {
                        if (it.value) {
                            NavHost(
                                navController = navController,
                                startDestination = NavDestinations.Home.route
                            ) {
                                composable(NavDestinations.Home.route) {
                                    HomeScreen()
                                }
                            }
                        } else {
                            AuthScreen()
                        }
                    }
                }
            }
        }
    }
}