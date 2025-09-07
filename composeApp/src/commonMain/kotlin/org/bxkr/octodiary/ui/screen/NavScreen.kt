package org.bxkr.octodiary.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                { Text("так называемый NavScreen") }
            )
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            Text("welcome", Modifier.align(Alignment.Center))
        }
    }
}
