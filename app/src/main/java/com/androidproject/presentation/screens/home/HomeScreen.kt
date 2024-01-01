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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.R

/**
 * The home screen
 * @param paddingValues the padding values to use
 * @param navToSearch the navigation method passed from Navigation
 */
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
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
        }
        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_text1),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_text2),
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
            Text(text = stringResource(R.string.discover_button))
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )

        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_madeby),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_madeby_name),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_for),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Label(
            label = { }) {
            Text(
                text = stringResource(R.string.welcome_poweredby),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Image(
            painter = painterResource(
            id = R.drawable.tmdb
            ),
            contentDescription = "TMDB",
            modifier = Modifier.size(100.dp, 100.dp)
        )
    }
}

/**
 * Preview of [HomeScreen]
 */
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(paddingValues = PaddingValues())
}
