package com.lordmorduk.simplewarcardgame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CompDeck(
    initCoordinated: DataCoordinates = DataCoordinates(),
    boxDimensions: DataSize = DataSize()
) {
    val deckOfCards = Deck.defaultDeck()
    deckOfCards.trueRandomShuffle()
    for (card in deckOfCards) {
        DeckCard(
            title = card.toSymbolString(),
            modifier = Modifier
        ) {

        }
    }
}