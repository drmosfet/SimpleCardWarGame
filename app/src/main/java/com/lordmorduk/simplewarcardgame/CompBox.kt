package com.lordmorduk.simplewarcardgame

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CompBox(title: String,
            initCoordinated: DataCoordinates = DataCoordinates(),
            boxDimensions: DataSize = DataSize(),
            placementDimensions: DataSize = DataSize(),
            lockCoordinated: DataCoordinates = DataCoordinates(),
            test: Boolean = false,
            z: Float = 0f,
            content: @Composable() () -> Unit)
{
    val context = LocalContext.current
    val displayMetrics = context.getResources().getDisplayMetrics()
    /// These two are only for the sake of Testing
    val dmWidthInPx = displayMetrics.widthPixels
    val dmHeightInPx = displayMetrics.heightPixels
    val cardInitWidth = boxDimensions.width
    val cardInitHeight = boxDimensions.height
    val cardInitWidthInPx = with(LocalDensity.current) { (cardInitWidth).dp.toPx() }
    val cardInitHeightInPx = with(LocalDensity.current) { (cardInitHeight).dp.toPx() }
    val marginSpace = 150
    var lockCoordinatedMarginXLow = lockCoordinated.x - marginSpace
    var lockCoordinatedMarginXHigh = lockCoordinated.x + marginSpace
    var lockCoordinatedMarginYLow = lockCoordinated.y - marginSpace
    var lockCoordinatedMarginYHigh = lockCoordinated.y + marginSpace

    val placementDimensionsWidthInPx = with(LocalDensity.current) { (placementDimensions.width).dp.toPx() }
    val placementDimensionsHeightInPx = with(LocalDensity.current) { (placementDimensions.height).dp.toPx() }

    val expandValue = boxDimensions.expand
    val fontSize = 10.sp
    val enabledDragging = remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shape = RoundedCornerShape(12.dp)
    val coroutineScope = rememberCoroutineScope()
    val clickable = Modifier.clickable(
        interactionSource = interactionSource,
        indication = LocalIndication.current
    ) { }
    val size = animateSizeAsState(
        targetValue = if (enabledDragging.value && !isPressed) {
            Size(width = cardInitWidth, height = cardInitHeight)
        } else {
            Size(width = cardInitWidth + expandValue, height = cardInitHeight + expandValue)
        }
    )
    var offsetX  =  remember { Animatable(initialValue = initCoordinated.x) }
    var offsetY  =  remember { Animatable(initialValue = initCoordinated.y) }

    var zV = remember { mutableStateOf(z) }
    val atLockPosition = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(
                zIndex = if (enabledDragging.value && !isPressed) {
                    zV.value.toFloat()
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
                .clip(shape)
                .background(color = MaterialTheme.colors.primary)
                .border(BorderStroke(1.dp, Color.Black), shape = shape)
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
                            if (offsetX.value in lockCoordinatedMarginXLow..lockCoordinatedMarginXHigh && offsetY.value in lockCoordinatedMarginYLow..lockCoordinatedMarginYHigh) {
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
        ) {
            if (test) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "Title: $title",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "Box Width: ${size.value.width}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Box Height: ${size.value.height}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Box Expand: $expandValue",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "init-X: ${initCoordinated.x.toString()}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "init-Y: ${initCoordinated.y.toString()}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "offset-X: ${offsetX.value.roundToInt().toString()}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "offset-Y: ${offsetY.value.roundToInt().toString()}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "z-Index: ${zV.value.toFloat()}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "At Lock Position: ${atLockPosition.value}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            content()
                        }
                    }
                }
            }
        }
    }
    if (test) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize(Alignment.BottomCenter)
        ) {
            Row()
            {
                Text(
                    text = "dmWidthInPx: $dmWidthInPx",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
                Text(
                    text = "dmHeightInPx: $dmHeightInPx",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
            }
            Row()
            {
                Text(
                    text = "cardInitWidth: $cardInitWidth",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
                Text(
                    text = "cardInitHeight: $cardInitHeight",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
            }
            Row()
            {
                Text(
                    text = "cardInitWidthInPx: $cardInitWidthInPx",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
                Text(
                    text = "cardInitHeightInPx: $cardInitHeightInPx",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
            }
            Row()
            {
                Text(
                    text = "middleLockScopeX: ${(dmWidthInPx - cardInitWidthInPx ) / 2}",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
                Text(
                    text = "middleLockScopeY: ${(dmHeightInPx - cardInitHeightInPx) / 2}",
                    color = Color.Black,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompBox() {
    CompBox(title = "Card Title", initCoordinated = DataCoordinates(100f, 100f), boxDimensions = DataSize(135f, 190f, 20f), test = false, content =
    {
        Text(text = "Title", color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
    }
    )
}