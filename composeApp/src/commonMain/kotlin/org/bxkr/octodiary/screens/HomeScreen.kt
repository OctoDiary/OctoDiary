package org.bxkr.octodiary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.delay
import org.bxkr.octodiary.data.StorageLatest
import org.koin.compose.koinInject


@Composable
fun HomeScreen() {
    val kStore: KStore<StorageLatest> = koinInject()
    var token by remember { mutableStateOf("nill") }
    LaunchedEffect(Unit) {
        delay(3000)
        kStore.update { currentStore: StorageLatest? ->
            currentStore?.copy(accessToken = "hello!!!")
        }
        delay(100)
        token = kStore.get()?.accessToken ?: "still nill :("
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Your token is $token")
    }
}