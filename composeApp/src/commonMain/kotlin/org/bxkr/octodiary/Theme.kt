package org.bxkr.octodiary

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun OctoDiaryTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme(),
        content = content,
        typography = Typography()
    )
}

//fun getColorScheme() = darkColorScheme(
//    surface = Color.Black,
//    onSurface = Color.White,
//    background = Color.Black,
//    surfaceContainer = Color.Black
//)