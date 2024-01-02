package com.androidproject.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
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
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navToSearch: (() -> Unit)? = null
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Text(
                text = stringResource(R.string.welcome_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_text1),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_text2),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            OutlinedButton(
                onClick = {
                    if (navToSearch != null) {
                        navToSearch()
                    }
                }
            ) {
                Text(text = stringResource(R.string.discover_button))
            }
        }

        item {
            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_madeby),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_madeby_name),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_for),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Text(
                text = stringResource(R.string.welcome_poweredby),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Image(
                painter = painterResource(
                    id = R.drawable.tmdb
                ),
                contentDescription = "TMDB",
                modifier = Modifier.size(100.dp, 100.dp)
            )
        }
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
