package com.lordmorduk.simplewarcardgame.MREs

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.lordmorduk.simplewarcardgame.CompBox
import com.lordmorduk.simplewarcardgame.DataCoordinates
import com.lordmorduk.simplewarcardgame.DataSize
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DataSize(val width: Float = 0f, val height: Float = 0f, val expand: Float = 0f) {}

data class DataCoordinates(val x: Float = 0f, val y: Float = 0f) {}

@Composable
fun CardBox(
    title: String,
    initCoordinated: DataCoordinates = DataCoordinates(),
    boxDimensions: DataSize = DataSize(),
    placementDimensions: DataSize = DataSize(),
    lockCoordinated: DataCoordinates = DataCoordinates(),
    z: Float = 0f,
    content: @Composable() () -> Unit
) {
    val cardInitWidth = boxDimensions.width
    val cardInitHeight = boxDimensions.height
    val cardInitWidthInPx = with(LocalDensity.current) { (cardInitWidth).dp.toPx() }
    val cardInitHeightInPx = with(LocalDensity.current) { (cardInitHeight).dp.toPx() }
    val marginSpace = 150
    val lockCoordinatedMarginLow = DataCoordinates(x = lockCoordinated.x - marginSpace, y = lockCoordinated.y - marginSpace)
    val lockCoordinatedMarginHigh = DataCoordinates(x= lockCoordinated.x + marginSpace, y = lockCoordinated.y + marginSpace)
    val placementDimensionsWidthInPx = with(LocalDensity.current) { (placementDimensions.width).dp.toPx() }
    val placementDimensionsHeightInPx = with(LocalDensity.current) { (placementDimensions.height).dp.toPx() }
    val enabledDragging = remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val coroutineScope = rememberCoroutineScope()
    val clickable = Modifier.clickable(interactionSource = interactionSource, indication = LocalIndication.current) {}
    val size = animateSizeAsState(targetValue = if (enabledDragging.value && !isPressed) { Size(width = cardInitWidth, height = cardInitHeight) } else { Size(width = cardInitWidth + boxDimensions.expand, height = cardInitHeight + boxDimensions.expand) })
    val offsetX  =  remember { Animatable(initialValue = initCoordinated.x) }
    val offsetY  =  remember { Animatable(initialValue = initCoordinated.y) }
    val zV = remember { mutableStateOf(z) }
    val atLockPosition = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(
                zIndex = if (enabledDragging.value && !isPressed) {
                    zV.value
                } else {
                    100f
                }
            )
            .offset {
                IntOffset(
                    x = offsetX.value.roundToInt(),
                    y = offsetY.value.roundToInt()
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(size.value.width.dp, size.value.height.dp)
                .background(color = MaterialTheme.colors.primary)
                .border(BorderStroke(1.dp, Color.Black))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            enabledDragging.value = !enabledDragging.value
                        },
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            coroutineScope.launch {
                                offsetX.snapTo(targetValue = offsetX.value + dragAmount.x)
                                offsetY.snapTo(targetValue = offsetY.value + dragAmount.y)
                            }
                            spring(stiffness = Spring.StiffnessHigh, visibilityThreshold = 0.1.dp)
                        },
                        onDragEnd = {
                            enabledDragging.value = !enabledDragging.value
                            // If I'm heating the middle pack location
                            // the card coordinates stick
                            if (offsetX.value in lockCoordinatedMarginLow.x..lockCoordinatedMarginHigh.x && offsetY.value in lockCoordinatedMarginLow.y..lockCoordinatedMarginHigh.y) {
                                atLockPosition.value = true
                                coroutineScope.launch {
                                    launch {
                                        offsetY.animateTo(
                                            targetValue = lockCoordinated.y + ((placementDimensionsHeightInPx - cardInitHeightInPx) / 2),
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                delayMillis = 50,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )
                                    }
                                    offsetX.animateTo(
                                        targetValue = lockCoordinated.x + ((placementDimensionsWidthInPx - cardInitWidthInPx) / 2),
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            delayMillis = 50,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                }
                            } else {
                                atLockPosition.value = false
                                coroutineScope.launch {
                                    launch {
                                        offsetY.animateTo(
                                            targetValue = initCoordinated.y,
                                            animationSpec = tween(
                                                durationMillis = 700,
                                                delayMillis = 50,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )
                                    }
                                    offsetX.animateTo(
                                        targetValue = initCoordinated.x,
                                        animationSpec = tween(
                                            durationMillis = 700,
                                            delayMillis = 50,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                }
                            }
                            spring(stiffness = Spring.StiffnessLow, visibilityThreshold = 0.1.dp)
                        }
                    )
                }
                .then(clickable)
        )
    }
}

@Composable
fun CardHand(
    cardDimensions: DataSize = DataSize(),
    placementDimentions: DataSize = DataSize(),
    lockCoordinated: DataCoordinates = DataCoordinates(),
    numberOfCards: Int
) {
    val context = LocalContext.current
    val displayMetrics = context.getResources().getDisplayMetrics()
    val dmWidthInPx = displayMetrics.widthPixels
    val cardDimensionsInPx = with(LocalDensity.current) { (cardDimensions.width.dp.toPx()) }

    val singleCardOffset = 100
    val cardCounter by remember { mutableStateOf(numberOfCards) }

    repeat(cardCounter) {
        val dataCoordinates = DataCoordinates(x = (dmWidthInPx - ((cardDimensionsInPx) + ((cardCounter - 1) * singleCardOffset))) / 2 , y = 0f)
        CardBox(
            title = "Box_$cardCounter",
            initCoordinated = DataCoordinates(x = dataCoordinates.x + (it * singleCardOffset), y = 50f),
            boxDimensions = cardDimensions,
            placementDimensions = placementDimentions,
            lockCoordinated = lockCoordinated,
            z = cardCounter.toFloat(),
            content = {}
        )
    }
}