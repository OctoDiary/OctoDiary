package org.bxkr.octodiary.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedVisibilityFade(
    visible: Boolean,
    modifier: Modifier = Modifier,
    inSpec: FiniteAnimationSpec<Float>? = null,
    outSpec: FiniteAnimationSpec<Float>? = null,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible,
        modifier,
        fadeIn(inSpec ?: spring(stiffness = Spring.StiffnessMediumLow)),
        fadeOut(outSpec ?: spring(stiffness = Spring.StiffnessMediumLow))
    ) { content() }
}

@Composable
fun <T> AnimatedVisibilityFadeNotNull(
    target: T?,
    modifier: Modifier = Modifier,
    inSpec: FiniteAnimationSpec<Float>? = null,
    outSpec: FiniteAnimationSpec<Float>? = null,
    content: @Composable (target: T) -> Unit
) {
    AnimatedVisibility(
        target != null,
        modifier,
        fadeIn(inSpec ?: spring(stiffness = Spring.StiffnessMediumLow)),
        fadeOut(outSpec ?: spring(stiffness = Spring.StiffnessMediumLow))
    ) { if (target != null) content(target) }
}