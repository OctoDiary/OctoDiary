package org.bxkr.octodiary.ui.component.auth

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun AuthBigButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val paddingHorizontal by animateDpAsState(
        if (isPressed) 8.dp else 16.dp,
        spring(.1f) // goofy ahh animation
    )

    Button(
        onClick,
        modifier
            .padding(
                paddingHorizontal.let { if (it > 0.dp) it else 0.dp },
                16.dp
            )
            .fillMaxWidth()
            .height(80.dp),
        shape = shape,
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        content()
    }
}