package com.example.chessduo_300.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Custom Color Scheme
private val LightColors = lightColorScheme(
    primary = Color(0xFFB88762),      // Gold tone
    onPrimary = Color.Black,
    secondary = Color(0xFFEDD6B0),    // Light beige
    onSecondary = Color.Black,
    background = Color(0xFFF3D8B6),   // Creamy background
    onBackground = Color(0xFF2E2E2E), // Dark text
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFF8B0000),        // Deep red
    onError = Color.White
)

// Optional: Dark Theme
private val DarkColors = darkColorScheme(
    primary = Color(0xFFB88762),
    onPrimary = Color.Black,
    secondary = Color(0xFFEDD6B0),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF2E2E2E),
    onSurface = Color.White,
    error = Color(0xFFFF8A80),
    onError = Color.Black
)

@Composable
fun ChessDuo_300Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}