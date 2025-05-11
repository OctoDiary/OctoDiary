package org.bxkr.octodiary.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.bxkr.octodiary.data.Result

@Composable
fun <T> ResultBox(
    result: Result<T>,
    successBox: @Composable (Result.Success<T>) -> Unit,
    loadingBox: @Composable () -> Unit,
    errorBox: @Composable (Result.Error) -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center
) = Box(modifier, contentAlignment) {
    AnimatedVisibility(
        result is Result.Success,
        enter = fadeIn(),
        exit = fadeOut()
    ) { if (result is Result.Success) successBox(result) }
    AnimatedVisibility(
        result is Result.Loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) { loadingBox() }
    AnimatedVisibility(
        result is Result.Error,
        enter = fadeIn(),
        exit = fadeOut()
    ) { if (result is Result.Error) errorBox(result) }
}