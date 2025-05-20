package com.example.chessduo_300.view_model

import androidx.lifecycle.ViewModel
import com.example.chessduo_300.R
import com.example.chessduo_300.model.ChessOpening
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _increment = MutableStateFlow(0)
    val increment: StateFlow<Int> = _increment

    private val _gameTime = MutableStateFlow(180)
    val gameTime: StateFlow<Int> = _gameTime


    fun setIncrement(value: Int) {
        _increment.value = value
    }

    fun setGameTime(value: Int) {
        _gameTime.value = value
    }


    private val _openings = MutableStateFlow(
        listOf(
            ChessOpening("French Defense", R.drawable.italian, "Xld_sJwdk8k"),
            ChessOpening("Ruy Lopez Opening", R.drawable.ruy_lopez, "xD0iTgHMQVQ"),
            ChessOpening("Caro-Kann Defense", R.drawable.caro, "HvER2idtW6M"),
            ChessOpening("Scilian Defense", R.drawable.sicilian, "2miolLK8DiI")
        )
    )

    val openings: StateFlow<List<ChessOpening>> = _openings
}


