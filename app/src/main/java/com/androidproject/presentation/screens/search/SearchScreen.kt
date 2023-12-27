package com.androidproject.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.R
import com.androidproject.data.movieSearchOptionsBools
import com.androidproject.data.movieSearchOptionsStrings
import com.androidproject.model.Genre
import com.androidproject.presentation.screens.misc.ErrorScreen
import com.androidproject.presentation.screens.misc.LoadingScreen
import com.androidproject.util.Resource

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    viewModel: SearchScreenViewModel = viewModel(
        factory = SearchScreenViewModel.Factory
    )
) {
    val state by viewModel.genresUiState.collectAsState()

    SearchScreen(
        paddingValues = paddingValues,
        genreUiState = state
    )
}

@Composable
fun SearchScreen(
    paddingValues : PaddingValues,
    genreUiState : Resource<List<Genre>>
){
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )

        when (genreUiState) {
            is Resource.Loading -> {
                LoadingScreen()
            }

            is Resource.Error -> {
                ErrorScreen(
                    error = genreUiState.message ?: stringResource(R.string.unknownerror)
                )
            }

            is Resource.Success -> {
                genreUiState.data?.let {
                    SearchOptions(it)
                }
            }
        }

        HorizontalDivider()

        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
fun SearchOptions(genres: List<Genre>) {
    LazyHorizontalGrid(
        modifier = Modifier
            .height((movieSearchOptionsStrings.size).times(70).dp)
            .fillMaxWidth(),
        rows = GridCells.Fixed(movieSearchOptionsStrings.size),
        horizontalArrangement = Arrangement.Center
    ) {
        items(movieSearchOptionsStrings) { option ->
            Column {
                Card(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                OutlinedTextField(
                    value = "",
                    onValueChange = {}
                )
            }
        }
        item {
            GenresForSearch(genres)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.FixedSize(100.dp)
    ) {
        items(movieSearchOptionsBools) { option ->
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = option)
            }
        }
    }
}

@Composable
fun GenresForSearch(genres: List<Genre>) {
    Column {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "genre",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        var menuExpanded by remember {
            mutableStateOf(false)
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            genres.forEach{
                DropdownMenuItem(
                    text = { it.name },
                    onClick = {
                        menuExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen(paddingValues = PaddingValues())
}
