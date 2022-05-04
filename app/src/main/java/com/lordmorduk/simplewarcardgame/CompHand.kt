package com.lordmorduk.simplewarcardgame

import android.provider.ContactsContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lordmorduk.simplewarcardgame.ui.theme.SimpleWarCardGameTheme
import java.lang.Math.max
import java.lang.Math.min

@Composable
fun CompHand(
    cardDimensions: DataSize = DataSize(),
    placementDimentions: DataSize = DataSize(),
    lockCoordinated: DataCoordinates = DataCoordinates(),
    numberOfCards: Int
) {
    var initCoordinates = DataCoordinates()
    val context = LocalContext.current
    val displayMetrics = context.getResources().getDisplayMetrics()
    val dmWidthInPx = displayMetrics.widthPixels
    val dmHeightInPx = displayMetrics.heightPixels
    val cardDimensionsInPx = with(LocalDensity.current) { (cardDimensions.width.dp.toPx()) }

    val singleCardOffset = 100
    val cardCounter by remember { mutableStateOf(numberOfCards) }

    repeat(cardCounter) {
        val dataCoordinates = DataCoordinates(x = (dmWidthInPx - ((cardDimensionsInPx) + ((cardCounter - 1) * singleCardOffset))) / 2 , y = 0f)
        CompBox(
            title = "Box_$cardCounter",
            initCoordinated = DataCoordinates(x = dataCoordinates.x + (it * singleCardOffset), y = 50f),
            boxDimensions = cardDimensions,
            placementDimensions = placementDimentions,
            lockCoordinated = lockCoordinated,
            testData1 = true,
            testData2 = true,
            z = cardCounter.toFloat(),
            content = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CompHandPreview() {
    CompHand(numberOfCards = 5)
}
