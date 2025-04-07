package com.example.chessduo_300.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chessduo_300.model.ChessOpening
import com.example.chessduo_300.R
import com.example.chessduo_300.model.Screen
import com.example.chessduo_300.view_model.MainViewModel


@Composable
fun BottomNavBar(
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFB88762), Color(0xFFF3D8B6))
                )
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onHomeClick) {
            Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(200.dp))
        IconButton(onClick = onSettingsClick) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Black)
        }
    }
}



@Composable
fun OpeningCard(opening: ChessOpening, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = opening.imageRes),
                contentDescription = opening.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "Learn", style = MaterialTheme.typography.labelSmall)
            Text(text = opening.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun OpeningsSection(viewModel: MainViewModel, navController: NavController) {
    val openings by viewModel.openings.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Learn openings",
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "See all"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(openings) { opening ->
                OpeningCard(opening = opening) {
                    navController.navigate(Screen.VideoScreen.route + "/${opening.name}/${opening.videoUrl}")
                }
            }
        }
    }
}


@Composable
fun PlayBanner() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(16.dp)
            .clickable {
                Toast
                    .makeText(context, "Play clicked!", Toast.LENGTH_SHORT)
                    .show()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.play_banner),
                contentDescription = "Play Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient text on top
            Text(
                text = "PLAY",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Black, Color.Red)
                    ),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
            )

        }
    }
}

@Composable
fun ClockBanner() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
            .padding(16.dp)
            .clickable {
                Toast
                    .makeText(context, "Play clicked!", Toast.LENGTH_SHORT)
                    .show()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFB88762), Color(0xFFEDD6B0)) // brown-beige gradient
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Chess clock",
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Black, Color.Red)
                    )
                )
            )
        }
    }
}

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))  // Push down by 100dp
        Text(
            text = "ChessDuo",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black, Color(0xFFB88762))
                ),
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .padding(start = 15.dp, top = 12.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )
        PlayBanner()
        OpeningsSection(viewModel, navController)
        ClockBanner()
        Spacer(modifier = Modifier.height(30.dp))
        BottomNavBar(
            onHomeClick = { /* Handle home click */ },
            onSettingsClick = { /* Handle settings click */ }
        )
    }
}