package com.androidproject.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.androidproject.presentation.navigation.Destinations.SEARCH_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(paddingValues: PaddingValues, navHostController: NavHostController? = null) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Label(
            label = { }) {
                Text(
                    text = "Welcome to the Home Page",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
        }
        Label(
            label = { }) {
            Text(
                text = "Here you'll be able to find random movies to watch",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Label(
            label = { }) {
            Text(
                text = "Use the bottom menu to start searching!",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = {
                navHostController?.navigate(SEARCH_ROUTE)
            }
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(paddingValues = PaddingValues())
}
