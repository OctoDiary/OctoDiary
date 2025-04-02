package org.bxkr.octodiary.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.bxkr.octodiary.data.auth.PreAuthManager

@Composable
fun AuthScreen() {
    val coroutineScope = rememberCoroutineScope()
    Button({
        coroutineScope.launch {
            PreAuthManager.setAccessToken("new token")
        }
    }) {
        Text("set example token")
    }
}