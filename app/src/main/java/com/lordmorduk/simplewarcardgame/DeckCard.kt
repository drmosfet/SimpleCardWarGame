package com.lordmorduk.simplewarcardgame

import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DeckCard(
    title: String,
    initCoordinated: DataCoordinates = DataCoordinates(),
    //boxDimensions: DataSize = DataSize(),
    z: Float = 0f,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val clickable = Modifier
        .clickable(
            interactionSource = interactionSource,
            indication = LocalIndication.current
        ) { }
    val size2 = animateValueAsState(targetValue = , typeConverter = )

    val size = animateSizeAsState(
        targetValue = if (!isPressed) {
            Size(width = 50f, height = 50f)
        } else {
            Size(width = 110f, height = 110f)
        }
    )
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            //.fillMaxSize()
            .aspectRatio(0.704545454f)
    ) {
        Box(
            modifier = modifier
                //.fillMaxSize()
                .size(size.value.width.dp, size.value.height.dp)
                .clip(shape)
                .background(color = MaterialTheme.colors.primary)
                .border(BorderStroke(1.dp, Color.Black), shape = shape)
                .then(clickable)
        )
        {
            content
        }
    }
}