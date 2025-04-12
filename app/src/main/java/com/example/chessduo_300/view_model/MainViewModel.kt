package com.example.chessduo_300.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chessduo_300.R
import com.example.chessduo_300.model.ChessOpening
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _openings = MutableStateFlow(
        listOf(
            ChessOpening("French Defense", R.drawable.italian, "FgAL6T_KILw"),
            ChessOpening("Ruy Lopez Opening", R.drawable.ruy_lopez, "https://media.chesscomfiles.com/videos/square/POL-ADV-popular-1e4-openings-ruylopez-italian-scotch-r3m.mp4"),
            ChessOpening("Caro-Kann Defense", R.drawable.caro, "https://media.chesscomfiles.com/videos/square/ENG-OPN_Caro-Kann_n9v.mp4"),
            ChessOpening("Scilian Defense", R.drawable.sicilian, "https://media.chesscomfiles.com/videos/square/POL-ADV-popular-1e4-openings-sicilian-defense-x9z.mp4")
        )
    )
    val openings: StateFlow<List<ChessOpening>> = _openings
}
