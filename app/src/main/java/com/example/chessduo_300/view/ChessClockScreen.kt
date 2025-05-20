import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.chessduo_300.view_model.MainViewModel


@Composable
fun ChessClockScreen(viewModel: MainViewModel, navController: NavController) {
    val gameTime by viewModel.gameTime.collectAsState()
    val increment by viewModel.increment.collectAsState()

    val whiteTime = remember { mutableStateOf(gameTime) }
    val blackTime = remember { mutableStateOf(gameTime) }

    var turn by remember { mutableStateOf(0) } // 0 = white, 1 = black
    var running by remember { mutableStateOf(true) }

    // Timer
    LaunchedEffect(turn, running) {
        while (running && whiteTime.value > 0 && blackTime.value > 0) {
            delay(1000L)
            if (turn % 2 == 0) {
                whiteTime.value = (whiteTime.value - 1).coerceAtLeast(0)
                if (whiteTime.value == 0) running = false
            } else {
                blackTime.value = (blackTime.value - 1).coerceAtLeast(0)
                if (blackTime.value == 0) running = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Half (Black Clock)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent),
                        )
                    )
                    .clickable(enabled = turn % 2 != 0 && whiteTime.value > 0 && running) {
                        blackTime.value += increment
                        turn++
                    }
            ) {
                Text(
                    text = formatTime(blackTime.value),
                    color = Color.White,
                    fontSize = 48.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer(rotationZ = 180f)
                )
                if (turn % 2 == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                    )
                }
            }

            // Bottom Half (White Clock)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        )
                    )
                    .clickable(enabled = turn % 2 == 0 && blackTime.value > 0 && running) {
                        whiteTime.value += increment
                        turn++
                    }
            ) {
                Text(
                    text = formatTime(whiteTime.value),
                    color = Color.White,
                    fontSize = 48.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                if (turn % 2 != 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                    )
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
}