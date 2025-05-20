package com.example.chessduo_300.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chessduo_300.view_model.MainViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel) {
    val timeOptions = listOf(1, 3, 5, 10, 30, 60, 120) // minutes
    val incrementOptions = listOf(0, 1, 2, 5) // seconds

    var selectedTime by remember { mutableStateOf(viewModel.gameTime.value / 60) }
    var selectedIncrement by remember { mutableStateOf(viewModel.increment.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Select Game Time", style = MaterialTheme.typography.headlineSmall)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                timeOptions.take(4).forEach { time ->
                    Button(
                        onClick = { selectedTime = time },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTime == time) Color(0xFF8B5E3C) else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("$time min")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                timeOptions.drop(4).forEach { time ->
                    Button(
                        onClick = { selectedTime = time },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTime == time) Color(0xFF8B5E3C) else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text("$time min")
                    }
                }
            }
        }

        Text("Select Increment per Move", style = MaterialTheme.typography.headlineSmall)


        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            incrementOptions.forEach { inc ->
                Button(
                    onClick = { selectedIncrement = inc },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedIncrement == inc) Color(0xFF8B5E3C) else Color.LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Text("+${inc}s")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {

        }
        Button(
            onClick = {
                // Update ViewModel with selected time
                viewModel.setGameTime(selectedTime * 60)
                viewModel.setIncrement(selectedIncrement)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5E3C))
        ) {
            Text("Save Settings", color = Color.White, modifier = Modifier.padding(horizontal = 24.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                navController.navigate("chess/${selectedTime * 60}/${selectedIncrement}")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5E3C))
        ) {
            Text("Start Game", color = Color.White, modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}

