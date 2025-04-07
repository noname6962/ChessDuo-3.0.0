package com.example.chessduo_300.view
import androidx.media3.common.MediaItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.chessduo_300.R


@Composable
fun VideoScreen(title: String, videoUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFB88762), Color(0xFFF3D8B6))
                )
            )
    ) {
        // Title
        Text(
            text = "Learn $title",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF8B0000),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )

        // Chessboard "video cover" with play icon
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.chessboard_placeholder), // replace with your drawable
                contentDescription = "Chessboard",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
        }

        // ExoPlayer view
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = ExoPlayer.Builder(context).build().apply {
                        val mediaItem = MediaItem.fromUri(videoUrl) // ✅ CORRECT
                        setMediaItem(mediaItem)
                        prepare()
                        playWhenReady = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(bottom = 16.dp)
        )
    }
}
