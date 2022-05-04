package com.lordmorduk.simplewarcardgame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
fun CompPlacement(
    title: String = "", initCoordinated: DataCoordinates = DataCoordinates(),
    boxDimensions: DataSize = DataSize(),
    test: Boolean = false,
    center: Boolean = false
)
{
    val shape = RoundedCornerShape(12.dp)
    val fontSize = 8.sp
    val context = LocalContext.current
    val displayMetrics = context.getResources().getDisplayMetrics()
    val dmWidthInPx = displayMetrics.widthPixels
    val dmHeightInPx = displayMetrics.heightPixels
    val placementWidthInPx = with(LocalDensity.current) { (boxDimensions.width.dp.toPx()) }
    val placementHeightInPx = with(LocalDensity.current) { (boxDimensions.height.dp.toPx()) }
    val middleLockScopeX = (dmWidthInPx - placementWidthInPx ) /2
    val middleLockScopeY = (dmHeightInPx - placementHeightInPx) /2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f)
            .offset { if(center) {
                IntOffset(
                    x = middleLockScopeX.roundToInt(),
                    y = middleLockScopeY.roundToInt()
                )
            } else {
                IntOffset(
                    x = 0,
                    y = 0
                )
            }
            }
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = initCoordinated.x.roundToInt(),
                        y = initCoordinated.y.roundToInt()
                    )
                }
                .size(boxDimensions.width.dp, boxDimensions.height.dp)
                .clip(shape)
                .background(Color(0xFF5FA777))
                .border(BorderStroke(1.dp, Color.Black), shape = shape)
            //.align( if (center) { Alignment.Center } else { Alignment.TopStart })
        )
        {
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
                                text = "Box Width: ${boxDimensions.width}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Box Height: ${boxDimensions.height}",
                                color = Color.White,
                                fontSize = fontSize,
                                textAlign = TextAlign.Center
                            )
                        }
                        if (!center) {
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
                            Column()
                            {
                                Text(
                                    text = "placementWidthInPx: $placementWidthInPx",
                                    color = Color.White,
                                    fontSize = fontSize,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
                                Text(
                                    text = "placementHeightInPx: $placementHeightInPx",
                                    color = Color.White,
                                    fontSize = fontSize,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompPlacement() {
    CompPlacement(
        title = "Title",
        initCoordinated = DataCoordinates(500f, 500f),
        boxDimensions = DataSize(155f, 210f),
        test = true,
        center = true
    )
}
