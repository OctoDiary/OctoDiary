package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.bxkr.octodiary.asClipEntry
import org.bxkr.octodiary.domain.model.auth.TokenInfo
import org.bxkr.octodiary.domain.model.user.UserType
import org.bxkr.octodiary.getText
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.toHumanTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun AuthTokenPrompt(
    viewModel: AuthViewModel = koinViewModel(),
//    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val tokenInfoFlow = uiState.tokenInfoFlow
    var tokenValue by remember { mutableStateOf("") }
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(tokenValue) {
        if (tokenValue.length > 2 && tokenValue.isNotBlank()) {
            viewModel.checkToken(tokenValue)
        }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            tokenValue,
            { tokenValue = it },
            Modifier.fillMaxWidth().padding(horizontal = 64.dp, vertical = 16.dp),
            label = { Text("Введите токен") },
            trailingIcon = { IconButton({
                coroutineScope.launch {
                    clipboard.getClipEntry()?.getText()?.let { tokenValue = it }
                }
            }) {
                Icon(
                    Icons.Rounded.ContentPaste,
                    "Вставить"
                )
            } },
            singleLine = true
        )
        AnimatedVisibility(tokenInfoFlow != null) {
            if (tokenInfoFlow != null) TokenInfo(tokenInfoFlow)
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TokenInfo(tokenInfoFlow: StateFlow<TokenInfo>) {
    val tokenInfo by tokenInfoFlow.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val standard = (tokenInfo as? TokenInfo.Standard) ?:
        (tokenInfo as? TokenInfo.Extended)?.standardInfo
        val extended = tokenInfo as? TokenInfo.Extended
        AnimatedVisibility(standard != null) {
            standard?.let { StandardTokenInfo(it) }
        }
        AnimatedVisibility(extended != null) {
            extended?.let { ExtendedTokenInfo(it) }
        }

        AnimatedVisibility(tokenInfo is TokenInfo.Loading || (tokenInfo as? TokenInfo.Standard)?.furtherLoading == true) {
            LinearWavyProgressIndicator()
        }
        AnimatedVisibility(standard?.cannotLoadFurther == true) {
            Text("Не удалось загрузить дополнительную информацию", Modifier.padding(top = 16.dp), textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun StandardTokenInfo(standard: TokenInfo.Standard) = Column {
    with (standard) {
        val keyValues = mapOf(
            "Тип токена" to actualDiaryName,
            "Действует до" to expiryTime?.toHumanTime(),
            "Создан" to issueTime?.toHumanTime(),
            "Пользователь" to userId
        )
        keyValues.forEach {
            val value = it.value
            if (value != null) {
                TokenInfoRow(it.key, value)
            }
        }
    }
}

@Composable
private fun ExtendedTokenInfo(extended: TokenInfo.Extended) = Column {
    with (extended) {
        val keyValues = mapOf(
            "Имя аккаунта" to userName,
            "Имя ученика" to studentName,
            "Тип аккаунта" to role?.let {
                when (it) {
                    UserType.Student -> "Ученик"
                    UserType.Parent -> "Родитель"
                }
            },
            "Школа" to extended.schoolName
        )
        keyValues.forEach {
            val value = it.value
            if (value != null) {
                TokenInfoRow(it.key, value)
            }
        }
    }
}

@Composable
private fun TokenInfoRow(name: String, value: String) {
    val coroutineScope = rememberCoroutineScope()
    val localClipboard = LocalClipboard.current
    Row(Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name)
        Spacer(Modifier.width(16.dp))
        Text(value, Modifier.clickable {
            coroutineScope.launch { localClipboard.setClipEntry(value.asClipEntry()) }
        }, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}