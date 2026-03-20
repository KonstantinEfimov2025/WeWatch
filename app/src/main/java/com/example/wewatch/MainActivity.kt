package com.example.wewatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wewatch.ui.theme.WeWatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MovieDatabase.getDatabase(this)
        val repository = MovieRepository(database.movieDao())
        val controller = MovieController(repository)

        setContent {
            WeWatchTheme {
                WeWatchApp(controller)
            }
        }
    }
}

@Composable
fun WeWatchApp(controller: MovieController) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                controller = controller,
                onAddClick = { navController.navigate("search") }
            )
        }

        composable("search") {
            SearchScreen(
                controller = controller,
                onMovieSelected = { movie ->
                    controller.selectMovie(movie)
                    navController.navigate("add")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("add") {
            AddScreen(
                controller = controller,
                onMovieAdded = {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}