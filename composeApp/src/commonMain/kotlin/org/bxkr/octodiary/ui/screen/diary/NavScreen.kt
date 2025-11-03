package org.bxkr.octodiary.ui.screen.diary

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.HomeWork
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.app_name
import octodiary4.composeapp.generated.resources.dashboard
import octodiary4.composeapp.generated.resources.diary
import octodiary4.composeapp.generated.resources.homeworks
import octodiary4.composeapp.generated.resources.marks
import octodiary4.composeapp.generated.resources.profile
import org.bxkr.octodiary.presentation.viewmodel.NavViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.bxkr.octodiary.ui.screen.diary.home.HomeScreen
import org.bxkr.octodiary.ui.screen.diary.home.HomeTitle
import org.bxkr.octodiary.ui.screen.diary.homeworks.HomeworksActions
import org.bxkr.octodiary.ui.screen.diary.homeworks.HomeworksScreen
import org.bxkr.octodiary.ui.screen.diary.homeworks.HomeworksTitle
import org.bxkr.octodiary.ui.screen.diary.marks.MarksActions
import org.bxkr.octodiary.ui.screen.diary.marks.MarksScreen
import org.bxkr.octodiary.ui.screen.diary.marks.MarksTitle
import org.bxkr.octodiary.ui.screen.diary.profile.ProfileActions
import org.bxkr.octodiary.ui.screen.diary.profile.ProfileScreen
import org.bxkr.octodiary.ui.screen.diary.profile.ProfileTitle
import org.bxkr.octodiary.ui.screen.diary.schedule.ScheduleActions
import org.bxkr.octodiary.ui.screen.diary.schedule.ScheduleScreen
import org.bxkr.octodiary.ui.screen.diary.schedule.ScheduleTitle
import org.bxkr.octodiary.ui.screen.settings.SettingsScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavScreen(
    navViewModel: NavViewModel = koinViewModel()
) {
    val uiState by navViewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Navigation(navViewModel)
        AnimatedVisibilityFade(uiState.isSettingsPageOpened) {
            SettingsScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Navigation(
    navViewModel: NavViewModel
) {
    val navController = rememberNavController()
    val startDestinationRoute = remember { NavDestination.Home.route }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentDestination = NavDestination.entries.find { it.route == currentRoute }

    LaunchedEffect(navController, navViewModel) {
        navViewModel.navigationEvents.collect { event ->
            navController.navigate(event.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            AnimatedContent(currentDestination) {
                when (it) {
                    NavDestination.Schedule -> ScheduleTitle()
                    NavDestination.Homeworks -> HomeworksTitle()
                    NavDestination.Home -> HomeTitle()
                    NavDestination.Marks -> MarksTitle()
                    NavDestination.Profile -> ProfileTitle()
                    else -> Text(stringResource(Res.string.app_name))
                }
            }
        }, actions = {
            when (currentDestination) {
                NavDestination.Schedule -> ScheduleActions()
                NavDestination.Homeworks -> HomeworksActions()
                NavDestination.Marks -> MarksActions()
                NavDestination.Profile -> ProfileActions()
                else -> {}
            }
        })
    }, bottomBar = {
        NavigationBar {
            NavDestination.entries.forEach {
                NavigationBarItem(
                    selected = currentRoute == it.route,
                    onClick = {
                        if (currentRoute != it.route) {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = when (it) {
                                NavDestination.Schedule -> Icons.AutoMirrored.Rounded.MenuBook
                                NavDestination.Homeworks -> Icons.Rounded.HomeWork
                                NavDestination.Home -> Icons.Rounded.Dashboard
                                NavDestination.Marks -> Icons.AutoMirrored.Rounded.TrendingUp
                                NavDestination.Profile -> Icons.Rounded.Person
                            }, contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(
                                resource = when (it) {
                                    NavDestination.Schedule -> Res.string.diary
                                    NavDestination.Homeworks -> Res.string.homeworks
                                    NavDestination.Home -> Res.string.dashboard
                                    NavDestination.Marks -> Res.string.marks
                                    NavDestination.Profile -> Res.string.profile
                                }
                            )
                        )
                    })
            }
        }
    }) { paddingValues ->
        NavHost(
            navController, startDestinationRoute, Modifier.padding(paddingValues)
        ) {
            composable(NavDestination.Schedule.route) { ScheduleScreen() }
            composable(NavDestination.Homeworks.route) { HomeworksScreen() }
            composable(NavDestination.Home.route) { HomeScreen(navViewModel) }
            composable(NavDestination.Marks.route) { MarksScreen() }
            composable(NavDestination.Profile.route) { ProfileScreen() }
        }
    }
}
