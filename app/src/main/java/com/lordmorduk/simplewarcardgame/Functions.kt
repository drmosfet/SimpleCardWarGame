package com.lordmorduk.simplewarcardgame

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun FindMiddleInPx(
    cardDimensions: DataSize = DataSize()
): DataCoordinates {
    val context = LocalContext.current
    //val displayMetrics = context.getResources().getDisplayMetrics()
    val displayMetrics = context.getResources().getDisplayMetrics()
    val cardInitWidth = cardDimensions.width
    val cardInitHeight = cardDimensions.height
    val cardInitWidthInPx = with(LocalDensity.current) { (cardInitWidth).dp.toPx() }
    val cardInitHeightInPx = with(LocalDensity.current) { (cardInitHeight).dp.toPx() }
    if (cardDimensions.width != 0f || cardDimensions.height != 0f) {
        return DataCoordinates((displayMetrics.widthPixels - cardInitWidthInPx ) / 2, (displayMetrics.heightPixels - cardInitWidthInPx ) / 2)
    } else {
        return DataCoordinates(displayMetrics.widthPixels.toFloat() / 2, displayMetrics.heightPixels.toFloat() / 2)
    }
}

@Composable
fun FindMiddleInDp(
    cardDimensions: DataSize = DataSize()
): DataCoordinates {
    val context = LocalContext.current
    val displayMetrics = context.getResources().getDisplayMetrics()
    val cardInitWidth = cardDimensions.width
    val cardInitHeight = cardDimensions.height
    val cardInitWidthInPx = with(LocalDensity.current) { (cardInitWidth).dp.toPx() }
    val cardInitHeightInPx = with(LocalDensity.current) { (cardInitHeight).dp.toPx() }
    if (cardDimensions.width != 0f || cardDimensions.height != 0f) {
        return DataCoordinates(((displayMetrics.widthPixels - cardInitWidthInPx )/displayMetrics.density) / 2, ((displayMetrics.heightPixels - cardInitWidthInPx )/displayMetrics.density) / 2)
    } else {
        return DataCoordinates((displayMetrics.widthPixels / displayMetrics.density) / 2, (displayMetrics.heightPixels.toFloat() / displayMetrics.density) / 2)
    }
}