package com.lordmorduk.simplewarcardgame

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


data class Point(val x: Int, val y: Int)
operator fun Point.unaryMinus() = Point(-x, -y)

data class DataCoordinatesXXX(var x: Float = 0f, var y: Float = 0f) {
    operator fun plus(increment: Float): DataCoordinatesXXX {
        return DataCoordinatesXXX(x = x + increment, y = y + increment)
    }
}
operator fun DataCoordinatesXXX.unaryMinus() = DataCoordinates(-x, -y)

fun <T> T.apply(block: T.() -> Unit): T { block(); return this }

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //assertEquals(4, 2 + 2)

        //val point = Point(-10, -20)
        //println(-point)

        //val x = DataCoordinatesXXX(10f, 10f)

//        println(x)
//        println(-x)
//        println(x+10f)

//        val foo: DataCoordinatesXXX = DataCoordinatesXXX().apply {
//            x = 10f
//            y = 10f
//        }

        val doc = Deck.defaultDeck()
        println(doc.toString())
        doc.trueRandomShuffle(seed = 367547543232753)
        println(doc.toString())
        val crd1 = Card(value=9, suit= Suit.SPADES)
        val crd2 = Card(value=13, suit= Suit.HEARTS)
        println("${crd1.toNameString()} ${crd1.toLetterString()} ${crd1.toSymbolString()}")
        println("${crd2.toNameString()} ${crd2.toLetterString()} ${crd2.toSymbolString()}")
        println(crd1.compareTo(crd2))
        doc.remove(crd1)
        doc.remove(crd2)
        println(doc.toString())

        println("rangeto function ${doc.rangeTo(5)}")

        val crd3 = Card.RandomCard
        println("random card1: ${Card.RandomCard}")
        println("random card2: ${Card.RandomCard}")
        println("random card3: ${Card.RandomCard}")
        println("random card4: ${Card.RandomCard}")
        println("random card: ${Card.get(1, 2, 3)}")
        println("doc.size: ${doc.size}")
        println("doc.firstCard: ${doc.firstCard}")
        println("doc.lastCard: ${doc.lastCard}")
        println("doc.middleCard: ${doc.middleCard}")
        println("doc.randomCard: ${doc.randomCard}")
        println("doc.isEmpty: ${doc.isEmpty}")
        println("doc.isNotEmpty: ${doc.isNotEmpty}")
        println("doc.get(3): ${doc.get(3)}")


        println(doc.draw(5))
        println(doc)
    }
}