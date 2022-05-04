package com.lordmorduk.simplewarcardgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lordmorduk.simplewarcardgame.ui.theme.SimpleWarCardGameTheme
import android.util.DisplayMetrics


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val placementDimentions = DataSize(width = 175.toFloat(), height = 230.toFloat())
        val context = this
        val displayMetrics = context.getResources().getDisplayMetrics()
        val dmWidthInPx = displayMetrics.widthPixels
        val dmHeightInPx = displayMetrics.heightPixels

        val placementInitYCenter = (dmHeightInPx/2 - (placementDimentions.height.toFloat()/2 * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)))
        val placementInitXCenter = (dmWidthInPx/2 - (placementDimentions.width.toFloat()/2 * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)))

        val placementInitX = placementInitXCenter
        val placementInitY = placementInitYCenter

        val placementCoordinates = DataCoordinates(x = 100f, y = placementInitY)

        setContent {
            SimpleWarCardGameTheme() {
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
                        center = false
                    )
                    for (i in 1 until 7) {
                        CompBox(
                            title = "Box_${i}",
                            initCoordinated = DataCoordinates(100f * i.toFloat(), 50f),
                            boxDimensions = DataSize(135f, 190f, 20f),
                            placementDimensions = placementDimentions,
                            lockCoordinated = placementCoordinates,
                            test = true,
                            z = i.toFloat(),
                            content = {})
                    }
                    for (i in 1 until 7) {
                        CompBox(
                            title = "Box_${i+6}",
                            initCoordinated = DataCoordinates(100f * i.toFloat(), 1600f),
                            boxDimensions = DataSize(135f, 190f, 20f),
                            placementDimensions = placementDimentions,
                            lockCoordinated = placementCoordinates,
                            test = true,
                            z = i+6.toFloat(),
                            content = {})
                    }
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