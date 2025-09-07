package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import octodiary4.composeapp.generated.resources.Res
import octodiary4.composeapp.generated.resources.ic_gosuslugi_znak
import octodiary4.composeapp.generated.resources.ic_mosru_id
import octodiary4.composeapp.generated.resources.ic_telegram_no_backdrop
import org.bxkr.octodiary.domain.model.auth.AuthMethod
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.bxkr.octodiary.ui.platform
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthSelectMethod(
    viewModel: AuthViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(
        uiState.selectedRegion,
        uiState.selectedDiary,
        uiState.authMethods
    ) {
        if (
            !uiState.isLoading
            && uiState.authMethods == null
            && uiState.selectedDiary != null
        ) viewModel.getAuthMethods()
    }

    Scaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                { Text(uiState.selectedDiary?.name ?: "Варианты входа") },
                subtitle = { Text("Выбери вариант входа") },
                navigationIcon = {
                    IconButton({ viewModel.goBack() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack, null
                        )
                    }
                })
        },
        snackbarHost = { SnackbarHost(mainViewModel.snackbarHostState) }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            AnimatedVisibilityFade(uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingIndicator()
                }
            }
            AnimatedVisibilityFade(uiState.authMethods != null) {
                val methodRepresentations =
                    viewModel
                        .filterAuthMethods(platform(), uiState.authMethods ?: emptyList())
                        .map { it.getRepresentation() }
                val preferable = methodRepresentations.filter { it.isPreferable }
                val additional = methodRepresentations.filterNot { it.isPreferable }
                var areAdditionalMethodsShown by remember { mutableStateOf(false) }
                var isOneClicked by remember { mutableStateOf(false) }

                val methodCard = @Composable { methodRepresentation: AuthMethodRepresentation ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    var isPressedVar by remember(isPressed) { mutableStateOf(isPressed) }
                    val scaleX by animateFloatAsState(
                        if (isPressedVar) 1.05f else 1f,
                        spring(.3f)
                    )
                    var isClicked by remember { mutableStateOf(false) }
                    val arrowOffset by animateDpAsState(
                        if (isClicked) 64.dp else 0.dp,
                        tween(300)
                    )
                    val arrowAlpha by animateFloatAsState(
                        if (isClicked) 0f else 1f,
                        tween(200)
                    )
                    val loadingIndicatorAlpha by animateFloatAsState(
                        if (isClicked) 1f else 0f,
                        tween(200, 100)
                    )
                    val cardAlpha by animateFloatAsState(
                        if (isOneClicked && !isClicked) .5f else 1f
                    )

                    Box(
                        Modifier
                            .scale(scaleX, 1f)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .alpha(cardAlpha)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(
                                Brush.horizontalGradient(
                                    0f to MaterialTheme.colorScheme.surfaceContainer,
                                    0.3f to MaterialTheme.colorScheme.surfaceContainer,
                                    1f to methodRepresentation.iconTint.copy(0.2f)
                                )
                            )
                            .run {
                                if (!isClicked && !isOneClicked) clickable(interactionSource = interactionSource) {
                                    isClicked = true
                                    isOneClicked = true
                                    viewModel.clickAuthMethod(methodRepresentation.method) {
                                        isClicked = false
                                        isOneClicked = false
                                    }
                                    if (!isPressed) coroutineScope.launch {
                                        isPressedVar = true
                                        delay(50)
                                        isPressedVar = false
                                    }
                                } else this
                            }
                    ) {
                        Row(Modifier.padding(16.dp)) {
                            Box(
                                Modifier
                                    .padding(end = 16.dp)
                                    .background(
                                        methodRepresentation.iconTint.copy(alpha = .2f),
                                        MaterialTheme.shapes.large
                                    )
                            ) {
                                methodRepresentation.iconComposable()
                            }
                            Row(
                                Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Row {
                                        Text(
                                            methodRepresentation.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        if (methodRepresentation.titleSuffix != null) {
                                            Text(
                                                " ${methodRepresentation.titleSuffix}",
                                                Modifier.alpha(.6f),
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }
                                    Text(methodRepresentation.description)
                                }
                                Box {
                                    Icon(
                                        Icons.AutoMirrored.Rounded.ArrowForward,
                                        "Продолжить",
                                        Modifier
                                            .offset(x = arrowOffset)
                                            .alpha(arrowAlpha)
                                            .padding(horizontal = 16.dp)
                                            .size(32.dp),
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    LoadingIndicator(
                                        Modifier
                                            .alpha(loadingIndicatorAlpha)
                                            .padding(horizontal = 16.dp)
                                            .size(32.dp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                LazyColumn(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    items(preferable) { methodRepresentation ->
                        methodCard(methodRepresentation)
                    }
                    item {
                        AnimatedVisibility(
                            !areAdditionalMethodsShown,
                            exit = shrinkVertically(
                                shrinkTowards = Alignment.Top
                            ) + fadeOut()
                        ) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isPressed by interactionSource.collectIsPressedAsState()
                            val scaleY by animateFloatAsState(
                                if (isPressed) .8f else 1f
                            )
                            val paddingVertical by animateDpAsState(
                                if (isPressed) 8.dp else 16.dp
                            )

                            Row(
                                Modifier.fillMaxWidth()
                                    .clickable(interactionSource = interactionSource) {
                                        areAdditionalMethodsShown = true
                                    }
                                    .scale(1f, scaleY)
                                    .padding(vertical = paddingVertical),
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Показать другие",
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Icon(
                                    Icons.Rounded.KeyboardArrowDown,
                                    null,
                                    Modifier.size(24.dp),
                                    MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }
                        AnimatedVisibility(
                            areAdditionalMethodsShown,
                            enter = expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                additional.forEach { methodCard(it) }
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class AuthMethodRepresentation(
    val method: AuthMethod,
    val title: String,
    val description: String,
    val icon: Pair<ImageVector?, DrawableResource?>,
    val iconTint: Color,
    val titleSuffix: String? = null,
    val isPreferable: Boolean = false
)


@Composable
private fun AuthMethod.AccessToken.TokenFormat.getName() = when(this) {
    AuthMethod.AccessToken.TokenFormat.School -> "стандартный"
    AuthMethod.AccessToken.TokenFormat.Uchebnik -> "Библиотека МЭШ"
}


@Suppress("SimplifiableCallChain") // https://youtrack.jetbrains.com/issue/KT-58031
@Composable
private fun AuthMethod.getRepresentation(): AuthMethodRepresentation {
    val esiaColor = Color(0xFF0066B3)
    val mosColor = Color(0xFFE51740)
    val telegramColor = Color(0xFF2AABEE)

    return when (this) {
        is AuthMethod.AccessToken -> AuthMethodRepresentation(
            this,
            "Токен доступа",
            if (supportedTokenFormatsHint == null) "Разовая авторизация с помощью токена доступа, активная до окончания жизни токена" else
                "Разовая авторизация с помощью токена доступа. Поддерживаемые форматы: ${supportedTokenFormatsHint.map { it.getName() }.joinToString(", ")}",
            Icons.Rounded.Key to null,
            MaterialTheme.colorScheme.secondary
        )

        is AuthMethod.InBrowser.Esia -> AuthMethodRepresentation(
            this,
            "Госуслуги",
            "Вход через ЕСИА в вашем браузере",
            null to Res.drawable.ic_gosuslugi_znak,
            esiaColor,
            "в браузере",
            isPreferable = true
        )

        is AuthMethod.InBrowser.MosRu -> AuthMethodRepresentation(
            this,
            "mos.ru",
            "Вход через mos.ru в вашем браузере",
            null to Res.drawable.ic_mosru_id,
            mosColor,
            "в браузере",
            isPreferable = true
        )

        is AuthMethod.LoginPassword -> AuthMethodRepresentation(
            this,
            "Логин и пароль",
            "Вход по логину и паролю от дневника",
            Icons.Rounded.Password to null,
            MaterialTheme.colorScheme.secondary,
            isPreferable = true
        )

        is AuthMethod.Telegram -> AuthMethodRepresentation(
            this,
            "Telegram-бот",
            "Дополнительные варианты входа с помощью нашего бота",
            null to Res.drawable.ic_telegram_no_backdrop,
            telegramColor
        )

        is AuthMethod.WebView.Esia -> AuthMethodRepresentation(
            this,
            "Госуслуги",
            "Вход через ЕСИА внутри приложения",
            null to Res.drawable.ic_gosuslugi_znak,
            esiaColor,
            "в приложении",
            isPreferable = true
        )

        is AuthMethod.WebView.MosRu -> AuthMethodRepresentation(
            this,
            "mos.ru",
            "Вход через mos.ru внутри приложения",
            null to Res.drawable.ic_mosru_id,
            mosColor,
            "в приложении",
            isPreferable = true
        )
    }
}

private val AuthMethodRepresentation.iconComposable: (@Composable () -> Unit)
    get() = {
        val modifier = Modifier.padding(12.dp).size(16.dp)
        if (icon.first != null) Icon(
            icon.first!!,
            null,
            modifier,
            iconTint
        ) else Icon(
            painterResource(icon.second!!),
            null,
            modifier,
            iconTint
        )
    }