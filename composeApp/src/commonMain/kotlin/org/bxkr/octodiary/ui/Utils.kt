package org.bxkr.octodiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.Platform
import org.bxkr.octodiary.data.Result
import org.bxkr.octodiary.getPlatform
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.currentKoinScope

@Composable
fun <T> Flow<Result<T>>.collectResult(): State<Result<T>> = collectAsState(Result.Loading)

@Composable
fun StringResource.resolve(): String = stringResource(this)

@Composable
fun StringResource.resolve(vararg params: Any): String = stringResource(this, formatArgs = params)

@Composable
fun StringResource.resolve(vararg params: StringResource): String =
    resolve(params = params.map { it.resolve() }.toTypedArray())

@Composable
fun Int.pxToDp(): Dp = with(LocalDensity.current) { toDp() }

@Composable
fun TextUnit.toDp(): Dp = with(LocalDensity.current) { toDp() }

@Composable
fun platform(): Platform = getPlatform(currentKoinScope())