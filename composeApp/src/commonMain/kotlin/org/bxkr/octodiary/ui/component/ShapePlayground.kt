package org.bxkr.octodiary.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShapePlayground() {
    var selected1 by remember { mutableStateOf(MaterialShapes.Square) }
    var selected2 by remember { mutableStateOf(MaterialShapes.Diamond) }
    val morph = remember(selected1, selected2) {
        Morph(selected1, selected2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = if (isPressed) 1f else 0f,
        label = "progress",
        animationSpec = spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessMedium)
    )
    val shapes = mapOf(
        "Circle" to MaterialShapes.Circle,
        "Square" to MaterialShapes.Square,
        "Slanted" to MaterialShapes.Slanted,
        "Arch" to MaterialShapes.Arch,
        "Fan" to MaterialShapes.Fan,
        "Arrow" to MaterialShapes.Arrow,
        "SemiCircle" to MaterialShapes.SemiCircle,
        "Oval" to MaterialShapes.Oval,
        "Pill" to MaterialShapes.Pill,
        "Triangle" to MaterialShapes.Triangle,
        "Diamond" to MaterialShapes.Diamond,
        "ClamShell" to MaterialShapes.ClamShell,
        "Pentagon" to MaterialShapes.Pentagon,
        "Gem" to MaterialShapes.Gem,
        "Sunny" to MaterialShapes.Sunny,
        "VerySunny" to MaterialShapes.VerySunny,
        "Cookie4Sided" to MaterialShapes.Cookie4Sided,
        "Cookie6Sided" to MaterialShapes.Cookie6Sided,
        "Cookie7Sided" to MaterialShapes.Cookie7Sided,
        "Cookie9Sided" to MaterialShapes.Cookie9Sided,
        "Cookie12Sided" to MaterialShapes.Cookie12Sided,
        "Ghostish" to MaterialShapes.Ghostish,
        "Clover4Leaf" to MaterialShapes.Clover4Leaf,
        "Clover8Leaf" to MaterialShapes.Clover8Leaf,
        "Burst" to MaterialShapes.Burst,
        "SoftBurst" to MaterialShapes.SoftBurst,
        "Boom" to MaterialShapes.Boom,
        "SoftBoom" to MaterialShapes.SoftBoom,
        "Flower" to MaterialShapes.Flower,
        "Puffy" to MaterialShapes.Puffy,
        "PuffyDiamond" to MaterialShapes.PuffyDiamond,
        "PixelCircle" to MaterialShapes.PixelCircle,
        "PixelTriangle" to MaterialShapes.PixelTriangle,
        "Bun" to MaterialShapes.Bun,
        "Heart" to MaterialShapes.Heart
    )

    Surface(Modifier.fillMaxSize()) {
        Column {
            Row(Modifier.height(400.dp)) {
                Column {
                    Text("default")
                    LazyColumn {
                        items(shapes.toList()) { pair ->
                            Row(
                                Modifier.clickable { selected1 = pair.second },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected1 == pair.second,
                                    { selected1 = pair.second }
                                )
                                Text(pair.first)
                            }
                        }
                    }
                }
                Column {
                    Text("on press")
                    LazyColumn {
                        items(shapes.toList()) { pair ->
                            Row(
                                Modifier.clickable { selected2 = pair.second },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected2 == pair.second,
                                    { selected2 = pair.second }
                                )
                                Text(pair.first)
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .clip(MorphPolygonShape(morph, animatedProgress.value))
                    .background(Color(0xFF80DEEA))
                    .size(200.dp)
                    .clickable(interactionSource = interactionSource) {
                    }
            ) {
                Text("Click me!", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}