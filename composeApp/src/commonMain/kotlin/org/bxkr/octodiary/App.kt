package org.bxkr.octodiary

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.data.auth.PreAuthManager
import org.bxkr.octodiary.ui.NavDestinations
import org.bxkr.octodiary.ui.OctoDiaryTheme
import org.bxkr.octodiary.ui.OctoDiaryTopBar
import org.bxkr.octodiary.ui.TopBarManager
import org.bxkr.octodiary.ui.collectResult
import org.bxkr.octodiary.ui.screens.HomeScreen
import org.bxkr.octodiary.ui.screens.auth.AuthScreen
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val topBarManager = koinInject<TopBarManager>()
        val coroutineScope = rememberCoroutineScope()
        val routeFlow = koinInject<MutableStateFlow<NavDestinations>>()
        val navController = rememberNavController()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            topBarManager.clearActions()
            coroutineScope.launch {
                destination.route?.let { NavDestinations.entries.first { it1 -> it1.route == it } }
                    ?.let { routeFlow.emit(it) }
            }
        }
        val isAuthorized by PreAuthManager.isAuthorized().collectResult()
        OctoDiaryTheme {
            Scaffold(
                topBar = {
                    if (isAuthorized is Result.Success)
                        OctoDiaryTopBar((isAuthorized as Result.Success).value)
                    else OctoDiaryTopBar()
                }
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