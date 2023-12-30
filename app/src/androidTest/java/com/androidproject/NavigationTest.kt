package com.androidproject

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.androidproject.model.Genre
import com.androidproject.presentation.navigation.AppNavigation
import com.androidproject.presentation.navigation.Destinations
import com.androidproject.presentation.screens.search.SearchScreen
import com.androidproject.util.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    private lateinit var device: UiDevice
    private lateinit var navController: TestNavHostController
    private var testGenre : Genre = Genre(
        100,
        "TestGenre"
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavigation(navController = navController)
        }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Destinations.HOME_ROUTE)
    }

    @Test
    fun navigateToHomeScreen_navigatesCorrectly() {
        composeTestRule.onNodeWithContentDescription("Home", useUnmergedTree = true)
            .performClick()

        navController.assertCurrentRouteName(Destinations.HOME_ROUTE)
    }

    @Test
    fun navigateToSearchResultsScreen_navigatesCorrectly() {
        composeTestRule.onNodeWithContentDescription("Search", useUnmergedTree = true)
            .performClick()

        navController.assertCurrentRouteName(Destinations.SEARCH_ROUTE)

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                SearchScreen(
                    paddingValues = PaddingValues(50.dp),
                    genreUiState = Resource.Success(
                        listOf(testGenre)
                    )
                ) {
                    navController.navigate("searchresults/include_video=false")
                }
            }
        }

        composeTestRule.onNodeWithText("Discover", useUnmergedTree = true)
            .performClick()

        navController.assertCurrentRouteName(Destinations.SEARCH_RESULTS_ROUTE)
    }

    @Test
    fun navigateToSavedMoviesScreen_navigatesCorrectly() {
        composeTestRule.onNodeWithContentDescription("Saved", useUnmergedTree = true)
            .performClick()

        navController.assertCurrentRouteName(Destinations.SAVED_MOVIES)
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }
}
