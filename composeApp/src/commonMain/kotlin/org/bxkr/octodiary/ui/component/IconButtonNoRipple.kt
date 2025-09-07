package org.bxkr.octodiary.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role

@ExperimentalMaterial3ExpressiveApi
@Composable
fun IconButtonNoRipple(
    onClick: () -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Box(
        modifier =
            modifier
                .minimumInteractiveComponentSize()
                .size(IconButtonDefaults.smallContainerSize())
                .clip(shape)
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = null,
                ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}