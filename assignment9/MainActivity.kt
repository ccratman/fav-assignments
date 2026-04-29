package com.example.moviesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Simple theme (fixes MovieSearchTheme error)
@Composable
fun MovieSearchTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MovieSearchTheme {
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "movie_search") {
        composable("movie_search") {
            MovieSearchScreen(navController)
        }
        composable("movie_details/{imdbId}") { backStackEntry ->
            val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""
            MovieDetailsScreen(navController, imdbId)
        }
    }
}
