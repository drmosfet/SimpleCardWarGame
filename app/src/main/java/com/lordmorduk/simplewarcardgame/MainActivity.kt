package com.lordmorduk.simplewarcardgame

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lordmorduk.simplewarcardgame.ui.theme.SimpleWarCardGameTheme
import android.util.DisplayMetrics
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val displayMetrics = context.getResources().getDisplayMetrics()
        val dmWidthInPx = displayMetrics.widthPixels
        val dmHeightInPx = displayMetrics.heightPixels

        val placementDimentions = DataSize(width = 125f, height = 180f)
        val cardDimentions = DataSize(width = 115f, height = 170f, expand = 10f)

        setContent {
            SimpleWarCardGameTheme() {
                val placementInitYCenter = (dmHeightInPx.toFloat() / 2) - (with(LocalDensity.current) { (placementDimentions.height.dp.toPx()) } / 2)
                val placementInitXCenter = (dmWidthInPx.toFloat() / 2) - (with(LocalDensity.current) { (placementDimentions.width.dp.toPx()) } / 2)
                val placementInitX = placementInitXCenter
                val placementInitY = placementInitYCenter
                val placementCoordinates = DataCoordinates(x = 50f, y = placementInitY)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Opponent's winning counter
                                Text(text = "Win\nCounter")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(4F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                // Opponent's hand goes here
                                Text(text = "Opponent's\nHand")
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .weight(3F),
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1F)
                                        .border(BorderStroke(1.dp, Color.Black)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    // Kozzer Placement
                                    Text(text = "Kozzer\nCard")
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1F)
                                        .padding(vertical = 10.dp)
                                        .border(BorderStroke(1.dp, Color.Black)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    // Draw Deck
                                    //Text(text = "Draw\nDeck")
                                    DeckCard(title = "", initCoordinated = DataCoordinates(0f, 0f)) {}
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1F)
                                        .border(BorderStroke(1.dp, Color.Black)),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    // Dismissal Deck
                                    Text(text = "Trash\nDeck")
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(4F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Attack Field
                                Text(text = "Attack\nField")
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                        ){
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                // Player's winning counter
                                Text(text = "Win\nCounter")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(4F)
                                    .padding(5.dp)
                                    .border(BorderStroke(1.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                // Player's hand goes here
                                //Text(text = "Player's\nHand")
                                Box(
                                    modifier = Modifier
                                        .background(color = Color.Red)
                                        .fillMaxHeight()
                                        .aspectRatio(0.704545454f)
                                ) {

                                }
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
fun DefaultPreview() {
    SimpleWarCardGameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {


        }
    }
}