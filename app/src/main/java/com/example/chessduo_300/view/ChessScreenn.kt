package com.example.chessduo_300.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chessduo_300.python.validateMove
import com.example.chessduo_300.python.checkMate

@Composable
fun ChessScreen() {
    val context = LocalContext.current
    val tileSize = 48.dp

    var isGameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<String?>(null) }

    var selectedSquare by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var turn by remember { mutableStateOf(0) }
    val moves = remember { mutableStateListOf<List<Any>>() }

    val whitePieces = remember {
        mutableStateListOf(
            mutableListOf("W", 1, 0, 0), // Rook
            mutableListOf("S", 1, 1, 0), // Knight
            mutableListOf("G", 1, 2, 0), // Bishop
            mutableListOf("D", 1, 3, 0), // Queen
            mutableListOf("K", 1, 4, 0), // King
            mutableListOf("G", 1, 5, 0), // Bishop
            mutableListOf("S", 1, 6, 0), // Knight
            mutableListOf("W", 1, 7, 0), // Rook
            mutableListOf("P", 1, 0, 1),
            mutableListOf("P", 1, 1, 1),
            mutableListOf("P", 1, 2, 1),
            mutableListOf("P", 1, 3, 1),
            mutableListOf("P", 1, 4, 1),
            mutableListOf("P", 1, 5, 1),
            mutableListOf("P", 1, 6, 1),
            mutableListOf("P", 1, 7, 1)
        )
    }

    val blackPieces = remember {
        mutableStateListOf(
            mutableListOf("W", 1, 0, 7), // Rook
            mutableListOf("S", 1, 1, 7), // Knight
            mutableListOf("G", 1, 2, 7), // Bishop
            mutableListOf("D", 1, 3, 7), // Queen
            mutableListOf("K", 1, 4, 7), // King
            mutableListOf("G", 1, 5, 7), // Bishop
            mutableListOf("S", 1, 6, 7), // Knight
            mutableListOf("W", 1, 7, 7), // Rook
            mutableListOf("P", 1, 0, 6),
            mutableListOf("P", 1, 1, 6),
            mutableListOf("P", 1, 2, 6),
            mutableListOf("P", 1, 3, 6),
            mutableListOf("P", 1, 4, 6),
            mutableListOf("P", 1, 5, 6),
            mutableListOf("P", 1, 6, 6),
            mutableListOf("P", 1, 7, 6)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ChessBoard(
            bialeFigury = whitePieces,
            czarneFigury = blackPieces,
            tileSize = tileSize,
            onTileClick = { x, y ->
                if (selectedSquare == null) {
                    selectedSquare = x to y
                } else {
                    val (fromX, fromY) = selectedSquare!!
                    selectedSquare = null

                    val vecX = x - fromX
                    val vecY = y - fromY

                    val isValid = validateMove(
                        context = context,
                        white = whitePieces.map { it.toList() },
                        black = blackPieces.map { it.toList() },
                        x = fromX,
                        y = fromY,
                        vecX = vecX,
                        vecY = vecY,
                        turn = turn,
                        moves = moves.map { it.toList() }
                    )

                    if (isValid) {
                        val movingPieces = if (turn % 2 == 0) whitePieces else blackPieces
                        val opponentPieces = if (turn % 2 == 0) blackPieces else whitePieces


                        val isOccupiedByOwnPiece = movingPieces.any {
                            val px = it[2] as? Int ?: -1
                            val py = it[3] as? Int ?: -1
                            val alive = it[1] as? Int ?: 0
                            px == x && py == y && alive == 1
                        }

                        if (isOccupiedByOwnPiece) {
                            println("Blocked by own piece at $x, $y")
                            return@ChessBoard
                        }


                        val target = opponentPieces.indexOfFirst { it[2] == x && it[3] == y && it[1] == 1 }
                        if (target != -1) {
                            val capturedPiece = opponentPieces[target]
                            capturedPiece[1] = 0
                            opponentPieces[target] = capturedPiece
                        }


                        val index = movingPieces.indexOfFirst { it[2] == fromX && it[3] == fromY }
                        if (index != -1) {
                            val piece = movingPieces[index]
                            val updatedPiece = mutableListOf(piece[0], piece[1], x, y)
                            movingPieces[index] = updatedPiece
                        }

                        turn += 1

                        val mate = checkMate(
                            context = context,
                            white = whitePieces.map { it.toList() },
                            black = blackPieces.map { it.toList() },
                            turn = turn,
                            moves = moves.map { it.toList() }
                        )

                        if (mate) {
                            isGameOver = true
                            winner = if (turn % 2 == 0) "Black Wins!" else "White Wins!"
                        }


                    }

                }
            }
        )
    }
}

@Composable
fun ChessBoard(
    bialeFigury: List<List<Any>>,
    czarneFigury: List<List<Any>>,
    tileSize: Dp,

    onTileClick: (Int, Int) -> Unit
) {
    Box {
        Column {
            for (y in 0..7) {
                Row {
                    for (x in 0..7) {
                        val isLight = (x + y) % 2 == 0
                        Box(
                            modifier = Modifier
                                .size(tileSize)
                                .background(if (isLight) Color(0xFFEAD7C0) else Color(0xFF8B5E3C))
                                .clickable { onTileClick(x, y) }
                        )
                    }
                }
            }
        }

        (bialeFigury + czarneFigury).filter { it[1] == 1 }.forEach { figura ->
            val isWhite = bialeFigury.contains(figura)
            val piece = figura[0] as String
            val x = figura[2] as Int
            val y = figura[3] as Int
            val imageId = getDrawableId(piece, isWhite)

            Image(
                painter = painterResource(id = imageId),
                contentDescription = piece,
                modifier = Modifier
                    .offset(x = tileSize * x, y = tileSize * y)
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
        "P" -> "p"  // pawn
        "W" -> "w"  // rook (wieża)
        "S" -> "s"  // knight (skoczek)
        "G" -> "g"  // bishop (goniec)
        "D" -> "d"  // queen (dama)
        "K" -> "k"  // king (król)
        else -> "p"
    }
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}
