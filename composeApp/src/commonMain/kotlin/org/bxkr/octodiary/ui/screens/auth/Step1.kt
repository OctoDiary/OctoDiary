package org.bxkr.octodiary.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.app_name
import kmpdiary.composeapp.generated.resources.diary_system
import kmpdiary.composeapp.generated.resources.more_details
import kmpdiary.composeapp.generated.resources.mosru_unavailable
import kmpdiary.composeapp.generated.resources.mosru_unavailable_description
import kmpdiary.composeapp.generated.resources.mosru_unavailable_title
import kmpdiary.composeapp.generated.resources.next_step
import kmpdiary.composeapp.generated.resources.region_not_implemented1
import kmpdiary.composeapp.generated.resources.region_not_implemented2
import kmpdiary.composeapp.generated.resources.region_not_implemented3
import kmpdiary.composeapp.generated.resources.select_region
import kmpdiary.composeapp.generated.resources.understood
import kmpdiary.composeapp.generated.resources.welcome
import org.bxkr.octodiary.Region
import org.bxkr.octodiary.data.ExternalIntegration
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.diaryName
import org.bxkr.octodiary.implementedRegions
import org.bxkr.octodiary.nameResource
import org.bxkr.octodiary.ui.collectResult
import org.bxkr.octodiary.ui.resolve
import org.bxkr.octodiary.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Step1() {
    val vm = koinViewModel<AuthViewModel>()
    LaunchedEffect(Unit) { vm.checkConnection() }
    val isConnected by remember { vm.isConnected }.collectResult()
    var isConnectionDialogShown by remember { mutableStateOf(false) }
    var isConnectionBubbleHidden by remember { mutableStateOf(false) }
    val currentChoice: MutableState<Region> = remember { mutableStateOf(vm.region.value) }
    val nextStepAllowed = currentChoice.value in implementedRegions

    if (isConnectionDialogShown) {
        AlertDialog(
            {
                isConnectionDialogShown = false
                isConnectionBubbleHidden = true
            },
            confirmButton = {
                TextButton({
                    isConnectionDialogShown = false
                    isConnectionBubbleHidden = true
                }) { Text(Res.string.understood.resolve()) }
            },
            title = { Text(Res.string.mosru_unavailable_title.resolve()) },
            text = { Text(Res.string.mosru_unavailable_description.resolve()) }
        )
    }

    Box(Modifier.fillMaxSize()) {
        AnimatedVisibility(
            isConnected is Result.Success &&
                    !(isConnected as Result.Success<Boolean>).value &&
                    !isConnectionBubbleHidden,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        isConnectionDialogShown = true
                    }
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.Warning,
                        null,
                        Modifier.size(32.dp).padding(8.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                    Text(
                        Res.string.mosru_unavailable.resolve() + ' ',
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        Res.string.more_details.resolve().lowercase(),
                        Modifier.alpha(.5f),
                        color = MaterialTheme.colorScheme.onError,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
        Column {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    Res.string.welcome.resolve(Res.string.app_name),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    Res.string.select_region.resolve(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        letterSpacing = 1.sp
                    )
                )
                Spacer(Modifier.size(64.dp))
                SelectRegionField(currentChoice)
            }
            Column(Modifier.fillMaxWidth()) {
                val alpha by animateFloatAsState(if (nextStepAllowed) 0f else 1f)
                Row(Modifier.alpha(alpha)) {
                    Text(
                        helpAddNewRegion(!nextStepAllowed),
                        Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center,
                        inlineContent = mapOf(
                            "icon" to InlineTextContent(
                                Placeholder(
                                    1.em,
                                    1.em,
                                    PlaceholderVerticalAlign.Center
                                )
                            ) {
                                Icon(
                                    Icons.Outlined.Info,
                                    null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        ),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                AnimatedContent(
                    nextStepAllowed,
                    transitionSpec = {
                        fadeIn().togetherWith(fadeOut())
                    }
                ) { targetState ->
                    Button(
                        {
                            vm.setRegion(currentChoice.value)
                            vm.goNext()
                        },
                        Modifier
                            .padding(8.dp)
                            .height(64.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        enabled = targetState
                    ) {
                        Row(
                            Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(Res.string.next_step.resolve())
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForward,
                                null
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRegionField(
    currentChoice: MutableState<Region>,
    modifier: Modifier = Modifier
) {
    var isOpened by remember { mutableStateOf(false) }
    val textFieldState = TextFieldValue(currentChoice.value.nameResource.resolve())
    val supportingText = buildAnnotatedString {
        appendInlineContent("icon")
        append(' ')
        append(
            @Suppress("SimplifiableCallChain") // https://youtrack.jetbrains.com/issue/KT-58031
            Res.string.diary_system.resolve(
                currentChoice.value.diaryName.map { it.resolve() }.joinToString()
            )
        )
    }
    Column {
        ExposedDropdownMenuBox(isOpened, { isOpened = !isOpened }) {
            AnimatedContent(
                textFieldState,
                transitionSpec = {
                    fadeIn(tween(200)).togetherWith(fadeOut(tween(200)))
                }
            ) { targetState ->
                OutlinedTextField(
                    targetState,
                    {},
                    modifier.width(280.dp).menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    singleLine = true,
                    readOnly = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isOpened) }
                )
            }
            ExposedDropdownMenu(isOpened, { isOpened = false }, Modifier.heightIn(max = 240.dp)) {
                Region.entries.forEach { region ->
                    DropdownMenuItem(
                        text = { Text(region.nameResource.resolve()) },
                        onClick = {
                            currentChoice.value = region
                            isOpened = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.size(2.dp))
        Text(
            supportingText,
            Modifier.padding(start = 16.dp).alpha(.8f),
            inlineContent = mapOf(
                "icon" to InlineTextContent(
                    Placeholder(
                        1.em,
                        1.em,
                        PlaceholderVerticalAlign.TextTop
                    )
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            ),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun helpAddNewRegion(isLinkEnabled: Boolean) = buildAnnotatedString {
    appendInlineContent("icon")
    append(' ')
    append(Res.string.region_not_implemented1.resolve())
    if (isLinkEnabled) {
        withLink(
            LinkAnnotation.Url(
                ExternalIntegration.TELEGRAM_NEW_REGION_LINK,
                styles = TextLinkStyles(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    pressedStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        background = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            )
        ) {
            append(Res.string.region_not_implemented2.resolve())
        }
    } else append(Res.string.region_not_implemented2.resolve())
    append(Res.string.region_not_implemented3.resolve())
}