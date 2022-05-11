package com.lordmorduk.simplewarcardgame

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//@Composable
//fun addCardToHand(
//    initCoordinated: DataCoordinates = DataCoordinates(),
//    content: @Composable() () -> Unit
//) {
//    CompBox(
//
//    )
//}
//
//@Composable
//fun removeCardFromHand(
//) {
//
//}









//fun CompHand(
//    cardDimensions: DataSize = DataSize(),
//    placementDimentions: DataSize = DataSize(),
//    lockCoordinated: DataCoordinates = DataCoordinates(),
//    cardCounter: Int = 0
//) {
//    var initCoordinates = DataCoordinates()
//    val context = LocalContext.current
//    val displayMetrics = context.getResources().getDisplayMetrics()
//    val dmWidthInPx = displayMetrics.widthPixels
//    val dmHeightInPx = displayMetrics.heightPixels
//    val cardDimensionsInPx = with(LocalDensity.current) { (cardDimensions.width.dp.toPx()) }
//    val singleCardOffset = 100
//    var numberOfCards = remember { mutableStateOf(cardCounter) }
//
//    repeat(numberOfCards.value) {
//        val dataCoordinates = DataCoordinates(x = (dmWidthInPx - ((cardDimensionsInPx) + ((cardCounter - 1) * singleCardOffset))) / 2 , y = 0f)
//        CompBox(
//            title = "Box_$cardCounter",
//            initCoordinated = DataCoordinates(x = dataCoordinates.x + (it * singleCardOffset), y = 50f),
//            boxDimensions = cardDimensions,
//            placementDimensions = placementDimentions,
//            lockCoordinated = lockCoordinated,
//            testData1 = false,
//            testData2 = false,
//            z = cardCounter.toFloat(),
//            content = {}
//        )
//    }
//}

//fun CompAddCard(
//    position: List<String> = listOf<String>("Top", "Bottom"),
//    cardDimensions: DataSize = DataSize(),
//    cardsAlreadyInHand: Int,
//    widthAlreadyUtilisedForCards: Float,
//    Card: @Composable () () -> Unit
//) {
//    getTheNumberOfCardsAlreadyInHand
//    getTheTotalWidth
//}

@Preview(showBackground = true)
@Composable
fun CompHandPreview() {
    //CompHand(numberOfCards = 5)
}
