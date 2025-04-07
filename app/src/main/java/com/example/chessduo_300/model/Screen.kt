package com.example.chessduo_300.model

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object VideoScreen : Screen("video_screen")

}