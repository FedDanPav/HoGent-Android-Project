package com.androidproject.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidproject.presentation.components.BottomBar
import com.androidproject.presentation.navigation.Destinations.HOME_ROUTE
import com.androidproject.presentation.navigation.Destinations.SAVED_MOVIES
import com.androidproject.presentation.navigation.Destinations.SEARCH_RESULTS_ROUTE
import com.androidproject.presentation.navigation.Destinations.SEARCH_ROUTE
import com.androidproject.presentation.screens.home.HomeScreen
import com.androidproject.presentation.screens.savedMovies.SavedMoviesScreen
import com.androidproject.presentation.screens.search.SearchScreen
import com.androidproject.presentation.screens.searchResults.SearchResultsScreen

/**
 * The different destinations in the app
 * @property HOME_ROUTE the home route
 * @property SEARCH_ROUTE the search route
 * @property SEARCH_RESULTS_ROUTE the search results route
 * @property SAVED_MOVIES the saved movies route
 */
object Destinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val SEARCH_RESULTS_ROUTE = "searchresults/{args}"
    const val SAVED_MOVIES = "savedmovies"
}

/**
 * The navigation controller of the app.
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
        ) {
            composable(HOME_ROUTE) {
                HomeScreen(paddingValues) {
                    navController.navigate(SEARCH_ROUTE)
                }
            }
            composable(SEARCH_ROUTE) {
                SearchScreen(paddingValues) { input ->
                    navController.navigate("searchresults/$input")
                }
            }
            composable(SEARCH_RESULTS_ROUTE) {
                SearchResultsScreen(paddingValues)
            }
            composable(SAVED_MOVIES) {
                SavedMoviesScreen(paddingValues)
            }
        }
    }
}
