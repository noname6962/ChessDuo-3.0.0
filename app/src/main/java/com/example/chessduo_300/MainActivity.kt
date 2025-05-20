

package com.example.chessduo_300

import ChessClockScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chessduo_300.ui.theme.ChessDuo_300Theme
import com.example.chessduo_300.view.BottomNavBar
import com.example.chessduo_300.view.ChessScreen
import com.example.chessduo_300.view.MainScreen
import com.example.chessduo_300.view.SettingsScreen
import com.example.chessduo_300.view.VideoScreen
import com.example.chessduo_300.view_model.MainViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessDuo_300Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ChessApp(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun ChessApp(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // Main scaffold layout
    Column(modifier = Modifier.fillMaxSize()) {
        // Top content (NavHost changes based on screen)
        Box(modifier = Modifier.weight(1f)) {
            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(navController = navController, viewModel = viewModel)
                }
                composable(
                    "video/{title}/{videoUrl}",
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("videoUrl") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val encodedUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
                    val videoUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name())

                    // You might need to pass lifecycleOwner here
                    val lifecycleOwner = LocalLifecycleOwner.current
                    VideoScreen(title = title, videoUrl = videoUrl, lifecycleOwner = lifecycleOwner)
                }
                composable("settings") {
                    SettingsScreen(navController = navController, viewModel = viewModel)
                }

                // Chess screen with time and increment
                composable(
                    "chess/{initialTime}/{increment}",
                    arguments = listOf(
                        navArgument("initialTime") { type = NavType.IntType },
                        navArgument("increment") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val initialTime = backStackEntry.arguments?.getInt("initialTime") ?: 180
                    val increment = backStackEntry.arguments?.getInt("increment") ?: 0
                    ChessScreen(initialTime = initialTime, increment = increment)
                }

                composable("clock") {
                    ChessClockScreen(viewModel = viewModel, navController = navController)
                }


            }
        }

        BottomNavBar(
            onHomeClick = { navController.navigate("main") },
            onSettingsClick = {navController.navigate("settings")}
        )
    }
}
