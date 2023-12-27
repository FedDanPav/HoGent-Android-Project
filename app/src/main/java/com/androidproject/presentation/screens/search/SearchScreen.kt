package com.androidproject.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@OptIn(ExperimentalMaterial3Api::class)
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
) {
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
                    SearchOptions(genres =  it)
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
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
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
            GenresForSearch(genres = genres)
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

@OptIn(ExperimentalMaterial3Api::class)
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
        var textFieldValue by remember { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = menuExpanded,
            onExpandedChange = {
                menuExpanded = !menuExpanded
            }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(), // menuAnchor modifier must be passed to the text field for correctness
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                },
                label = { Text("Genres") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            // filter options based on text field value
            val filteringOptions = genres.filter { it.name.contains(textFieldValue, ignoreCase = true) }
            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    filteringOptions.forEach { selectedGenre ->
                        DropdownMenuItem(
                            text = { Text(selectedGenre.name) },
                            onClick = {
                                textFieldValue = selectedGenre.name
                                menuExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen(paddingValues = PaddingValues())
}
