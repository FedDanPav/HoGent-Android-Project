package com.androidproject.presentation.components

import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.androidproject.R
import com.androidproject.presentation.navigation.Destinations.HOME_ROUTE
import com.androidproject.presentation.navigation.Destinations.SAVED_MOVIES
import com.androidproject.presentation.navigation.Destinations.SEARCH_ROUTE

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val badgeCount: Int? = null,
    val route: String,
    val onClick: () -> Unit,
)
@Composable
fun BottomBar(navHostController: NavHostController) {
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.home_button),
            selectedIcon = R.drawable.baseline_description_24,
            unselectedIcon = R.drawable.baseline_description_24,
            route = HOME_ROUTE
        ) {
            navHostController.navigate(HOME_ROUTE) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },

        BottomNavigationItem(
            title = stringResource(R.string.search_button),
            selectedIcon = R.drawable.baseline_search_24,
            unselectedIcon = R.drawable.baseline_search_24,
            route = SEARCH_ROUTE
        ) {
            navHostController.navigate(SEARCH_ROUTE) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },

        BottomNavigationItem(
            title = stringResource(R.string.saved_button),
            selectedIcon = R.drawable.baseline_favorite_24,
            unselectedIcon = R.drawable.baseline_favorite_24,
            route = SAVED_MOVIES
        ) {
            navHostController.navigate(SAVED_MOVIES) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(getDefaultSelectedItemIndex(items, "Nieuws"))
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        if (item.badgeCount != null) {
                            Text(text = item.badgeCount.toString())
                        }
                    }) {
                        Icon(
                            painter = if (index == selectedItemIndex) {
                                painterResource(id = item.selectedIcon)
                            } else {
                                painterResource(id = item.unselectedIcon)
                            },
                            contentDescription = item.title,
                        )
                    }
                },
                label = {
                    Text(text = item.title)
                },
                selected = (selectedItemIndex == index),
                onClick = {
                    selectedItemIndex = index
                    item.onClick()
                },
            )
        }
    }
}

fun getDefaultSelectedItemIndex(items: List<BottomNavigationItem>, defaultItemTitle: String): Int {
    return items.indexOfFirst { it.title == defaultItemTitle }
}

@Composable
@Preview
fun BottomBarPreview() {
    BottomBar(
        navHostController = NavHostController(LocalContext.current),
    )
}
