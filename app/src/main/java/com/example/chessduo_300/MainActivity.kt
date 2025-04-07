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
import com.example.chessduo_300.model.Screen
import com.example.chessduo_300.ui.theme.ChessDuo_300Theme
import com.example.chessduo_300.view.MainScreen
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
                    AppNavigation(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController, viewModel)
        }
        composable(
            route = Screen.VideoScreen.route + "/{title}/{videoUrl}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("videoUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = URLDecoder.decode(backStackEntry.arguments?.getString("title"), StandardCharsets.UTF_8.name())
            val videoUrl = URLDecoder.decode(backStackEntry.arguments?.getString("videoUrl"), StandardCharsets.UTF_8.name())
            VideoScreen(title, videoUrl)
        }
    }
}
