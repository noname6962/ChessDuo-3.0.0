

package com.example.chessduo_300

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
import com.example.chessduo_300.view.VideoScreen
import com.example.chessduo_300.viewmodel.MainViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessDuo_300Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BottomNavBar(
                        onHomeClick = { /* Handle home click */ },
                        onSettingsClick = { /* Handle settings click */ }
                    )
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
                composable("chess"){
                    ChessScreen()
                }
            }
        }

        BottomNavBar(
            onHomeClick = { navController.navigate("main") },
            onSettingsClick = {}
        )
    }
}


// Optional: Create this to avoid hardcoding route strings in multiple places
object NavRoutes {
    const val MAIN = "main"
    const val VIDEO = "video"
}
