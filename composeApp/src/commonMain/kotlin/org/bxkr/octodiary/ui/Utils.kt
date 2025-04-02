package org.bxkr.octodiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import org.bxkr.octodiary.data.Result

@Composable
fun <T> Flow<Result<T>>.collectResult(): State<Result<T>> = collectAsState(Result.Loading)