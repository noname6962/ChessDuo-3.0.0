package com.example.chessduo_300.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ChessScreen() {
    val whitePieces = listOf(
        listOf("P", 1, 0, 1),
        listOf("W", 1, 0, 0),
        listOf("K", 1, 4, 0)
    )

    val blackPieces = listOf(
        listOf("P", 1, 0, 6),
        listOf("W", 1, 0, 7),
        listOf("K", 1, 4, 7)
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ChessBoard(whitePieces, blackPieces)
    }
}

@Composable
fun ChessBoard(
    bialeFigury: List<List<Any>>, // list of ['P', 1, x, y]
    czarneFigury: List<List<Any>>
) {
    val tileSize = 48.dp
    Box {
        Column {
            for (row in 0..7) {
                Row {
                    for (col in 0..7) {
                        val isLight = (row + col) % 2 == 0
                        Box(
                            modifier = Modifier
                                .size(tileSize)
                                .background(if (isLight) Color(0xFFEAD7C0) else Color(0xFF8B5E3C))
                        )
                    }
                }
            }
        }

        // Draw white pieces
        bialeFigury.filter { it[1] == 1 }.forEach { figura ->
            val piece = figura[0] as String
            val x = figura[2] as Int
            val y = figura[3] as Int
            val imageId = getDrawableId(piece, true)

            Image(
                painter = painterResource(id = imageId),
                contentDescription = piece,
                modifier = Modifier
                    .offset(x = tileSize * x, y = tileSize * y)
                    .size(tileSize)
            )
        }

        // Draw black pieces
        czarneFigury.filter { it[1] == 1 }.forEach { figura ->
            val piece = figura[0] as String
            val x = figura[2] as Int
            val y = figura[3] as Int
            val imageId = getDrawableId(piece, false)

            Image(
                painter = painterResource(id = imageId),
                contentDescription = piece,
                modifier = Modifier
                    .offset(x =tileSize * x, y =tileSize * y)
                    .size(tileSize)
            )
        }
    }
}

@Composable
fun getDrawableId(letter: String, isWhite: Boolean): Int {
    val context = LocalContext.current
    val prefix = if (isWhite) "bialy_" else "czarny_"
    val resourceName = prefix + when (letter.uppercase()) {
        "P" -> "p"
        "W" -> "w"
        "S" -> "s"
        "G" -> "g"
        "K" -> "k"
        "D" -> "d"
        else -> "p"
    }
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}