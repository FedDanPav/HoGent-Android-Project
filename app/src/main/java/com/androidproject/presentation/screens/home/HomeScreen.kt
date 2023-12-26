package com.androidproject.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navToSearch: (() -> Unit)? = null
) {
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
                    text = "Welcome!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
        }
        Label(
            label = { }) {
            Text(
                text = "You'll be able to find random movies to watch",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Label(
            label = { }) {
            Text(
                text = "Use the bottom bar to start searching!",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = {
                if (navToSearch != null) {
                    navToSearch()
                }
            }
        ) {
            Text(text = "Search")
        }

        Label(
            label = { }) {
            Text(
                text = "Made by:",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = "Fedor Danilov",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = "For HoGent project",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = "Powered by",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Image(
            painter = painterResource(
            id = com.androidproject.R.drawable.tmdb
            ),
            contentDescription = "TMDB",
            modifier = Modifier.size(50.dp, 50.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(paddingValues = PaddingValues())
}
