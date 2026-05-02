package org.bxkr.octodiary.ui.screen.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.zIndex
import androidx.graphics.shapes.Morph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import octodiary4.composeapp.generated.resources.*
import org.bxkr.octodiary.domain.ExternalIntegration
import org.bxkr.octodiary.domain.model.region.Region
import org.bxkr.octodiary.domain.model.region.RegionCode
import org.bxkr.octodiary.presentation.viewmodel.AuthViewModel
import org.bxkr.octodiary.presentation.viewmodel.MainViewModel
import org.bxkr.octodiary.ui.component.IconButtonNoRipple
import org.bxkr.octodiary.ui.component.MorphPolygonShape
import org.bxkr.octodiary.ui.resolve
import org.bxkr.octodiary.ui.toPx
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthSelectRegion(
    viewModel: AuthViewModel = koinViewModel(),
    mainViewModel: MainViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState(1)
    val centerItemIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val center =
                layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2

            layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    val itemCenter = item.offset + item.size / 2
                    abs(center - itemCenter)
                }?.index
        }
    }
    val list = listOf(
        null,
        RegionListItem(
            RegionCode.MosReg,
            "Московская область", // warn: this string will go to domain
            Res.drawable.ic_50
        ),
        RegionListItem(
            RegionCode.Moscow,
            "Москва",
            Res.drawable.ic_77
        ),
        RegionListItem(
            RegionCode.Tatarstan,
            "Татарстан",
            Res.drawable.ic_16
        ),
        RegionListItem(
            RegionCode.Kaluga,
            "Калужская область",
            Res.drawable.ic_40
        ),
        RegionListItem(
            RegionCode.Dagestan,
            "Дагестан",
            Res.drawable.ic_05
        ),
        RegionListItem(
            RegionCode.Tumen,
            "Тюменская область",
            Res.drawable.ic_72
        ),
        RegionListItem(
            RegionCode.Chechnya,
            "Чечня",
            Res.drawable.ic_95
        ),
        RegionListItem(
            RegionCode.Yamal,
            "ЯНАО",
            Res.drawable.ic_89
        ),
        null
    )
    val supportedRegionCodes = listOf(
        RegionCode.Moscow,
        RegionCode.MosReg,
        RegionCode.Tatarstan
    )

    val selectedItemIndex = when (centerItemIndex) {
        0 -> 1
        list.lastIndex -> list.lastIndex - 1
        else -> centerItemIndex
    }
    val selectedRegion = selectedItemIndex?.let { list[it] }

    Box {
        Scaffold(
            Modifier,
            topBar = {
                LargeFlexibleTopAppBar(
                    { Text("Добро пожаловать!") },
                    Modifier,
                    subtitle = { Text("Выбери регион из списка") },
                    navigationIcon = {
                        var clickCount by remember { mutableIntStateOf(0) }
                        val mainUiState by mainViewModel.uiState.collectAsState()
                        val scale by animateFloatAsState(
                            if (clickCount <= 5) 1f + (.05f * clickCount) else 1f,
                            spring(.1f)
                        )
                        val tintColor by animateColorAsState(
                            if (mainUiState.isDebugMode) MaterialTheme.colorScheme.tertiary
                            else MaterialTheme.colorScheme.primary
                        )

                        LaunchedEffect(clickCount == 6) {
                            if (clickCount == 6) {
                                launch {
                                    mainViewModel.snackbarHostState.showSnackbar("Режим отладки включен")
                                }
                                mainViewModel.enableDebugMode()
                                mainViewModel.openDebugMenu()
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButtonNoRipple(
                                {
                                    if (clickCount <= 5 && !mainUiState.isDebugMode) clickCount += 1
                                    else mainViewModel.openDebugMenu()
                                },
                                RectangleShape,
                                Modifier
                                    .zIndex(1f)
                                    .padding(start = 8.dp)
                                    .scale(scale),
                            ) {
                                Icon(
                                    painterResource(Res.drawable.ic_simplified),
                                    null,
                                    tint = tintColor
                                )
                            }
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(mainViewModel.snackbarHostState)
            }
        ) { paddingValues ->
            Column(
                Modifier.padding(paddingValues).fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                val isUnsupportedRegion =
                    selectedRegion != null && selectedRegion.regionCode !in supportedRegionCodes
                val helperAlpha by animateFloatAsState(
                    if (isUnsupportedRegion) 1f else 0f
                )
                Row(Modifier.alpha(helperAlpha)) {
                    Text(
                        helpAddNewRegion(isUnsupportedRegion),
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
                Box {
                    val interactionSource = remember { MutableInteractionSource() }
                    val bigButton = @Composable { isEnabled: Boolean ->
                        AuthBigButton(
                            {
                                selectedRegion?.run {
                                    centerItemIndex?.let {
                                        viewModel.selectRegion(
                                            Region(regionCode.code, title),
                                            it
                                        )
                                    }
                                    viewModel.goNext()
                                }
                            },
                            enabled = isEnabled,
                            interactionSource = interactionSource
                        ) {
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
                    androidx.compose.animation.AnimatedVisibility(
                        selectedRegion != null && selectedRegion.regionCode in supportedRegionCodes,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        bigButton(true)
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        selectedRegion == null || selectedRegion.regionCode !in supportedRegionCodes,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        bigButton(false)
                    }
                }
            }
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                contentAlignment = Alignment.Center
            ) {
                val fullBoxWidth = remember { 144.dp }
                val fullBoxWidthPx = fullBoxWidth.toPx()
                val windowWidth = LocalWindowInfo.current.containerSize.width
                val centerOffset = windowWidth / 2 - fullBoxWidthPx / 2
                var itemsArePositioned by remember { mutableStateOf(false) }

                val scrollToIndex: suspend (Int) -> Unit = { toIndex ->
                    listState.animateScrollToItem(toIndex, -centerOffset.roundToInt())
                }

                val isDragged by listState.interactionSource.collectIsDraggedAsState()
                LaunchedEffect(isDragged) {
                    if (!isDragged && itemsArePositioned) {
                        delay(200.milliseconds)
                        centerItemIndex?.let { scrollToIndex(it) }
                    }
                }

                val morph = remember {
                    Morph(
                        MaterialShapes.ClamShell, MaterialShapes.Cookie9Sided
                    )
                }

                LaunchedEffect(itemsArePositioned) {
                    if (itemsArePositioned) scrollToIndex(uiState.initialRegionIndex ?: 2)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        Modifier.height(32.dp).fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp)
                        )
                    ) {}
                    LazyRow(
                        Modifier
                            .padding(vertical = 16.dp),
                        listState
                    ) {
                        itemsIndexed(list) { index, item ->
                            val itemOffset by remember {
                                derivedStateOf {
                                    val layoutInfo = listState.layoutInfo
                                    val visibleItems = layoutInfo.visibleItemsInfo
                                    val currentItem = visibleItems.find { it.index == index }
                                    currentItem?.let {
                                        val center =
                                            layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset / 2
                                        val itemCenter = it.offset + it.size / 2
                                        abs(center - itemCenter).toFloat()
                                    } ?: 0f
                                }
                            }

                            val effectStartDistance = 300f // расстояние от центра без эффекта
                            val maxOffset = 700f // крайняя граница эффекта

                            val adjustedOffset =
                                (itemOffset - effectStartDistance).coerceAtLeast(0f)
                            val normalized =
                                (adjustedOffset / (maxOffset - effectStartDistance)).coerceIn(
                                    0f,
                                    1f
                                )
                            val scale = 1f - 0.5f * normalized
                            val alpha = 1f - normalized

                            Row(
                                Modifier
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                        this.alpha = alpha
                                    }
                            ) {
                                if (index == 0 || index == list.lastIndex) Spacer(Modifier.width(160.dp)) else {
                                    val morphProgress by animateFloatAsState(
                                        if (centerItemIndex == index) 1f else 0f,
                                        spring(.4f)
                                    )
                                    val shape = MorphPolygonShape(morph, morphProgress)
                                    Box(
                                        Modifier
                                            .size(fullBoxWidth)
                                            .clip(shape)
                                            .clickable {
                                                coroutineScope.launch {
                                                    scrollToIndex(index)
                                                }
                                            }
                                            .background(MaterialTheme.colorScheme.surfaceContainer)
                                            .border(
                                                4.dp,
                                                MaterialTheme.colorScheme.secondary.copy(alpha = morphProgress),
                                                MorphPolygonShape(morph, morphProgress)
                                            )
                                            .padding(8.dp)
                                            .onGloballyPositioned { itemsArePositioned = true },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        item?.drawableRes?.let {
                                            Image(
                                                painterResource(it),
                                                null,
                                                Modifier.fillMaxSize().scale(1.1f),
                                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceContainerHigh)
                                            )
                                        }
                                        Text(
                                            item?.title ?: "",
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(Modifier.width(16.dp))
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
            }
        }
    }
}

private data class RegionListItem(
    val regionCode: RegionCode,
    val title: String,
    val drawableRes: DrawableResource?
)

@Composable
private fun helpAddNewRegion(isLinkEnabled: Boolean) = buildAnnotatedString {
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