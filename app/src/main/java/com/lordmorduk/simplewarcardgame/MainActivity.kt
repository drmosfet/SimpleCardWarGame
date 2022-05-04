package com.lordmorduk.simplewarcardgame

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lordmorduk.simplewarcardgame.ui.theme.SimpleWarCardGameTheme
import android.util.DisplayMetrics
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val displayMetrics = context.getResources().getDisplayMetrics()
        val dmWidthInPx = displayMetrics.widthPixels
        val dmHeightInPx = displayMetrics.heightPixels

        var placementDimentions = DataSize(width = 145f, height = 200f)
        var cardDimentions = DataSize()

        setContent {
            SimpleWarCardGameTheme() {
                //var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }

                val configuration = LocalConfiguration.current
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        placementDimentions = DataSize(width = 125f, height = 180f)
                        cardDimentions = DataSize(width = 115f, height = 170f, expand = 10f)
                    }

                    // Other wise
                    else -> {
                        placementDimentions = DataSize(width = 125f, height = 180f)
                        cardDimentions = DataSize(width = 115f, height = 170f, expand = 10f)
                    }
                }

                val placementInitYCenter = (dmHeightInPx.toFloat() / 2) - (with(LocalDensity.current) { (placementDimentions.height.dp.toPx()) } / 2)
                val placementInitXCenter = (dmWidthInPx.toFloat() / 2) - (with(LocalDensity.current) { (placementDimentions.width.dp.toPx()) } / 2)

                val placementInitX = placementInitXCenter
                val placementInitY = placementInitYCenter

                val placementCoordinates = DataCoordinates(x = 50f, y = placementInitY)

                val numberOfCards = remember { mutableStateOf(6) }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CompPlacement(
                        title = "Placement Centered",
                        boxDimensions = placementDimentions,
                        initCoordinated = placementCoordinates,
                        test = true,
                        center = false,
                    )
                    CompHand(
                        cardDimensions = cardDimentions,
                        placementDimentions = placementDimentions,
                        lockCoordinated = placementCoordinates,
                        numberOfCards = numberOfCards.value
                    )
//                    for (i in 1 until 7) {
//                        CompBox(
//                            title = "Box_",
//                            initCoordinated = DataCoordinates(100f, 50f),
//                            boxDimensions = cardDimentions,
//                            placementDimensions = placementDimentions,
//                            lockCoordinated = placementCoordinates,
//                            testData1 = true,
//                            testData2 = true,
//                            z = 0f,
//                            content = {})
//                    }
//                    for (i in 1 until 7) {
//                        CompBox(
//                            title = "Box_${i+6}",
//                            initCoordinated = DataCoordinates(100f * i.toFloat(), dmHeightInPx.toFloat() - (cardDimentions.height * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)) - 50),
//                            boxDimensions = cardDimentions,
//                            placementDimensions = placementDimentions,
//                            lockCoordinated = placementCoordinates,
//                            testData1 = true,
//                            testData2 = true,
//                            z = i+6.toFloat(),
//                            content = {})
//                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleWarCardGameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {


        }
    }
}