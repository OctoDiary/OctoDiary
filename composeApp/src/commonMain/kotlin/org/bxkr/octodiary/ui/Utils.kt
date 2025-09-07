package org.bxkr.octodiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.bxkr.octodiary.Platform
import org.bxkr.octodiary.getPlatform
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.currentKoinScope
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
fun Dp.toPx(): Float = with(LocalDensity.current) { toPx() }

@Composable
fun TextUnit.toDp(): Dp = with(LocalDensity.current) { toDp() }

@Composable
fun platform(): Platform = getPlatform(currentKoinScope())

@OptIn(ExperimentalTime::class)
@Composable
fun Instant.toHumanTime() = toLocalDateTime(TimeZone.currentSystemDefault()).format(LocalDateTime.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
    char(' ')
    hour(Padding.NONE)
    char(':')
    minute()
})