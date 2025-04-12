package com.example.chessduo_300

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chessduo_300.ui.theme.ChessDuo_300Theme
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
                    ChessApp(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun ChessApp(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "video/{title}/{videoUrl}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("videoUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val encodedUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
            val videoUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.name())
            VideoScreen(title = title, videoUrl = videoUrl)
        }
    }
}

// Optional: Create this to avoid hardcoding route strings in multiple places
object NavRoutes {
    const val MAIN = "main"
    const val VIDEO = "video"
}
