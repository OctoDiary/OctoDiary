package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import octodiary4.composeapp.generated.resources.*
import org.bxkr.octodiary.domain.ExternalIntegration
import org.bxkr.octodiary.domain.model.diary.DiaryId
import org.bxkr.octodiary.domain.model.region.RegionCode
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.component.AnimatedVisibilityFade
import org.bxkr.octodiary.ui.theme.LocalIsDark
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthSelectDiary(
    viewModel: AuthViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.availableDiaries, uiState.selectedRegion, uiState.isLoading) {
        if (uiState.run {
                (availableDiaries == null && selectedRegion != null && !isLoading) || (availableDiaries?.isNotEmpty() == true && availableDiaries.first().region != selectedRegion)
            }) {
            viewModel.getAvailableDiaries()
        }
    }

    Scaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                { Text(uiState.selectedRegion?.name ?: "Дневники") },
                subtitle = { Text("Выбери свой дневник") },
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
        Box(
            Modifier.fillMaxSize().padding(paddingValues)
        ) {
            AnimatedVisibilityFade(uiState.isLoading) {
                LoadingIndicator(Modifier.align(Alignment.Center))
            }
            AnimatedVisibilityFade(uiState.availableDiaries != null) {
                var selectedDiaryIndex by remember(
                    uiState.availableDiaries
                ) {
                    mutableStateOf(
                        uiState.availableDiaries?.indexOfFirst { it.id == uiState.selectedDiary?.id }
                            .let { if (it == -1 || it == null) 0 else it })
                }
                Column(Modifier.fillMaxSize()) {
                    Column(
                        Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val isDark = LocalIsDark.current
                        val iconTint =
                            if (isDark) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                        LazyColumn(
                            Modifier.padding(vertical = 32.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            val supported = uiState.availableDiaries ?: emptyList()
                            val planned = when (uiState.selectedRegion?.regionCode) {
                                RegionCode.Moscow.code -> listOf(
                                    PlannedDiary(
                                        "Госуслуги Моя школа",
                                        drawableRes = Res.drawable.ic_myschool_gosuslugi
                                    )
                                )

                                RegionCode.MosReg.code -> listOf(
                                    PlannedDiary(
                                        "Госуслуги Моя школа",
                                        drawableRes = Res.drawable.ic_myschool_gosuslugi
                                    ),
                                    PlannedDiary(
                                        "ЭлЖур",
                                        drawableRes = Res.drawable.ic_eljur
                                    )
                                )

                                else -> emptyList()
                            }
                            itemsIndexed(supported) { index, diary ->
                                val interactionSource = remember { MutableInteractionSource() }
                                val isPressed by interactionSource.collectIsPressedAsState()
                                var isPressedVar by remember(isPressed) { mutableStateOf(isPressed) }
                                val coroutineScope = rememberCoroutineScope()
                                val scaleX by animateFloatAsState(
                                    if (isPressedVar) 1.05f else 1f,
                                    spring(.2f)
                                )

                                val isSelected = selectedDiaryIndex == index
                                val icon: DrawableResource = when (diary.id) {
                                    DiaryId.MesMos -> Res.drawable.ic_mes_mos
                                    DiaryId.SpoMos -> Res.drawable.ic_mes_spo
                                    DiaryId.MesMosReg,
                                    DiaryId.MesTatarstan,
                                    DiaryId.MesKaluga -> Res.drawable.ic_mes_myschool

                                    DiaryId.Demo -> Res.drawable.ic_mes_mos // TODO: Implement icon for demo diary
                                }
                                val borderColor by animateColorAsState(
                                    if (isSelected) MaterialTheme.colorScheme.secondary
                                    else Color.Transparent,
                                    tween(300, 100)
                                )

                                Column(Modifier.padding(horizontal = 16.dp).scale(scaleX, 1f)) {
                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .clip(MaterialTheme.shapes.large)
                                            .clickable(interactionSource = interactionSource) {
                                                selectedDiaryIndex = index
                                                if (!isPressed) coroutineScope.launch {
                                                    isPressedVar = true
                                                    delay(50.milliseconds)
                                                    isPressedVar = false
                                                }
                                            }
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                            .border(
                                                2.dp,
                                                borderColor,
                                                MaterialTheme.shapes.large
                                            )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painterResource(icon),
                                                null,
                                                Modifier
                                                    .clip(MaterialTheme.shapes.small)
                                                    .size(32.dp),
                                                iconTint
                                            )
                                            Spacer(Modifier.width(16.dp))
                                            Text(diary.name)
                                        }
                                    }
                                    if (index != supported.lastIndex || planned.isNotEmpty()) {
                                        Spacer(Modifier.height(16.dp))
                                    }
                                }
                            }
                            itemsIndexed(planned) { index, plannedDiary ->
                                Column(Modifier.padding(horizontal = 16.dp)) {
                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .clip(MaterialTheme.shapes.large)
                                            .border(
                                                1.dp,
                                                MaterialTheme.colorScheme.outlineVariant,
                                                MaterialTheme.shapes.large
                                            )
                                    ) {
                                        Row(
                                            Modifier.padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            plannedDiary.drawableRes?.let {
                                                Icon(
                                                    painterResource(it),
                                                    null,
                                                    Modifier
                                                        .clip(MaterialTheme.shapes.small)
                                                        .size(32.dp),
                                                    iconTint
                                                )
                                                Spacer(Modifier.width(16.dp))
                                            }
                                            Column {
                                                Text(plannedDiary.title)
                                                Text(
                                                    helpAddDiaryString(plannedDiary),
                                                    style = MaterialTheme.typography.labelMedium
                                                )
                                            }
                                        }
                                    }
                                    if (index != planned.lastIndex) {
                                        Spacer(Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                        Card(
                            Modifier.height(32.dp).fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = MaterialTheme.shapes.extraLarge.copy(
                                bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)
                            )
                        ) {}
                    }
                    AuthBigButton({
                        uiState.availableDiaries?.getOrNull(selectedDiaryIndex)?.let {
                            viewModel.selectDiary(it)
                            viewModel.goNext()
                        }
                    }) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                "Продолжить",
                                Modifier.align(Alignment.Center),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                null,
                                Modifier.size(ButtonDefaults.IconSize)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class PlannedDiary(
    val title: String,
    val description: String? = null,
    val drawableRes: DrawableResource? = null,
    val needHelp: Boolean = true
)

@Composable
private fun helpAddDiaryString(plannedDiary: PlannedDiary) = buildAnnotatedString {
    append(plannedDiary.description ?: "Поддержка планируется.")
    append(' ')

    if (plannedDiary.needHelp) {
        withLink(
            LinkAnnotation.Url(
                ExternalIntegration.TELEGRAM_NEW_DIARY_LINK,
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
            append("Помочь с интеграцией")
        }
    }
}