package org.bxkr.octodiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import org.bxkr.octodiary.getDynamicColorScheme

val LocalIsDark = compositionLocalOf { true }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun OctoDiaryTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val darkScheme = getDynamicColorScheme(true) ?: darkColorScheme()
    val lightScheme = getDynamicColorScheme(false) ?: expressiveLightColorScheme()
    CompositionLocalProvider(LocalIsDark provides isDarkTheme) {
        MaterialExpressiveTheme(
            colorScheme = if (isDarkTheme) darkScheme else lightScheme,
            content = content,
            motionScheme = MotionScheme.expressive()
        )
    }
}