package org.bxkr.octodiary.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kmpdiary.composeapp.generated.resources.Res
import kmpdiary.composeapp.generated.resources.copy_error_description
import kmpdiary.composeapp.generated.resources.internal_error_occurred
import kmpdiary.composeapp.generated.resources.network_error_occurred
import kmpdiary.composeapp.generated.resources.server_error_occurred
import kmpdiary.composeapp.generated.resources.try_again
import org.bxkr.octodiary.data.Result
import org.jetbrains.compose.resources.stringResource

@Composable
fun DescribedLoading(
    title: String,
    steps: Map<String, MutableState<out Result<*>?>>,
    modifier: Modifier = Modifier,
    tryAgainOnClick: (() -> Unit)? = null
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        steps.forEach { step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                val iconSize = 16.dp
                AnimatedContent(
                    step.value.value,
                    contentAlignment = Alignment.Center
                ) { stepValue ->
                    when (stepValue) {
                        is Result.Loading -> CircularProgressIndicator(
                            Modifier.size(iconSize).alpha(.8f),
                            color = LocalContentColor.current
                        )

                        is Result.Success -> Icon(
                            Icons.Rounded.Done,
                            null,
                            Modifier.size(iconSize).alpha(.8f)
                        )

                        is Result.Error -> Icon(
                            Icons.Rounded.Close,
                            null,
                            Modifier.size(iconSize).alpha(.8f)
                        )

                        null -> Spacer(Modifier.size(iconSize))
                    }
                }
                Spacer(Modifier.size(8.dp))
                Text(step.key)
            }
        }
        Spacer(Modifier.height(16.dp))
        val errorStep = steps.entries.firstOrNull { it.value.value is Result.Error }
        AnimatedVisibility(errorStep != null) {
            if (errorStep != null) Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val error = errorStep.value.value as Result.Error
                val clipboardManager = LocalClipboardManager.current
                val errorDescription = """
                    |copied from DescribedLoading.
                    |step name: ${errorStep.key}

                    |${error.description}
                """.trimMargin()

                Text(
                    stringResource(
                        when (error.errorType) {
                            Result.ErrorType.Network -> Res.string.network_error_occurred
                            Result.ErrorType.Server -> Res.string.server_error_occurred
                            Result.ErrorType.Internal -> Res.string.internal_error_occurred
                        }
                    ),
                    style = MaterialTheme.typography.titleMedium
                )

                if (tryAgainOnClick != null) {
                    Button(
                        tryAgainOnClick,
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                    ) {
                        Icon(
                            Icons.Rounded.Refresh,
                            null,
                            Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(Res.string.try_again))
                    }
                }
                OutlinedButton({
                    clipboardManager.setText(AnnotatedString(errorDescription))
                }, contentPadding = ButtonDefaults.ButtonWithIconContentPadding) {
                    Icon(
                        Icons.Rounded.ContentCopy,
                        null,
                        Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(Res.string.copy_error_description))
                }
            }
        }
    }
}