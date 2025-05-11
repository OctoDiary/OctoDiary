package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.bxkr.octodiary.getService
import org.bxkr.octodiary.ui.resolve
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.jetbrains.compose.resources.StringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Step2() {
    val vm = koinViewModel<AuthViewModel>()
    vm
        .region
        .collectAsState()
        .value
        .getService()
        .Step2(vm)
}

data class FooterAdditionalIcon(
    val icon: ImageVector,
    val onClick: () -> Unit
)

data class FooterInput(
    val text: StringResource,
    val icon: ImageVector,
    val additionalIcon: FooterAdditionalIcon?,
    val isLoading: Boolean = false,
    val onClick: () -> Unit
)

@Composable
fun CommonFooter(
    vm: AuthViewModel,
    footerInput: FooterInput,
    doSoftTransition: Boolean = footerInput.additionalIcon == null
) {
    Row {
        FilledTonalIconButton(
            { vm.goBack() },
            Modifier
                .padding(start = 8.dp, bottom = 8.dp, top = 8.dp)
                .size(64.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                null
            )
        }
        AnimatedContent(
            footerInput,
            Modifier
                .weight(1f),
            transitionSpec = if (doSoftTransition) {
                {
                    fadeIn(tween(200)).togetherWith(fadeOut(tween(200, 200)))
                }
            } else {
                {
                    (fadeIn(
                        animationSpec = tween(
                            220,
                            delayMillis = 150
                        )
                    )).togetherWith(fadeOut(animationSpec = tween(150)))
                }
            }
        ) { targetState ->
            Row {
                Button(
                    { footerInput.onClick() },
                    Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(64.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(targetState.text.resolve())
                        Box(Modifier.size(24.dp)) {
                            androidx.compose.animation.AnimatedVisibility(
                                footerInput.isLoading,
                                enter = fadeIn(), exit = fadeOut()
                            ) {
                                Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(
                                        Modifier.size(16.dp),
                                        color = LocalContentColor.current,
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                            androidx.compose.animation.AnimatedVisibility(
                                !footerInput.isLoading,
                                enter = fadeIn(), exit = fadeOut()
                            ) {
                                Icon(
                                    targetState.icon,
                                    null,
                                    Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
                if (targetState.additionalIcon != null)
                    FilledTonalIconButton(
                        { targetState.additionalIcon.onClick() },
                        Modifier
                            .padding(end = 8.dp, bottom = 8.dp, top = 8.dp)
                            .size(64.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Icon(
                            targetState.additionalIcon.icon,
                            null
                        )
                    }
            }
        }
    }
}