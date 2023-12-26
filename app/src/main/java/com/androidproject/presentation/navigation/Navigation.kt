package com.androidproject.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.androidproject.presentation.components.BottomBar
import com.androidproject.presentation.navigation.Destinations.HOME_ROUTE
import com.androidproject.presentation.navigation.Destinations.SEARCH_ROUTE
import com.androidproject.presentation.screens.HomeScreen
import com.androidproject.presentation.screens.SearchScreen

object Destinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: HOME_ROUTE

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
                HomeScreen(paddingValues, navController)
            }
            composable(SEARCH_ROUTE) {
                SearchScreen(paddingValues)
            }
        }
    }
}