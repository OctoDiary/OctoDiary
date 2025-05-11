package org.bxkr.octodiary.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.bxkr.octodiary.ui.TopBarManager
import org.bxkr.octodiary.ui.viewmodel.HomeViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreen() {
    val vm: HomeViewModel = koinViewModel()
    val token by vm.getToken().collectAsState()
    val topBarManager: TopBarManager = koinInject()

    LaunchedEffect(Unit) { topBarManager.clearActions() }

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(token?.value ?: "null")
        Button({ vm.logOut() }) {
            Text("Log out")
        }
    }
}