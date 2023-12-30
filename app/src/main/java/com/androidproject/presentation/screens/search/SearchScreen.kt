package com.androidproject.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.R
import com.androidproject.data.movieSearchOptionsBools
import com.androidproject.data.movieSearchOptionsStrings
import com.androidproject.model.Genre
import com.androidproject.presentation.screens.shared.ErrorScreen
import com.androidproject.presentation.screens.shared.LoadingScreen
import com.androidproject.util.Resource
import java.lang.IndexOutOfBoundsException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    viewModel: SearchScreenViewModel = viewModel(
        factory = SearchScreenViewModel.Factory
    ),
    navigateToSearchResults : (input: String) -> Unit
) {
    val state by viewModel.genresUiState.collectAsState()

    SearchScreen(
        paddingValues = paddingValues,
        genreUiState = state,
        navigateToSearchResults = navigateToSearchResults
    )
}

@Composable
fun SearchScreen(
    paddingValues : PaddingValues,
    genreUiState : Resource<List<Genre>>,
    navigateToSearchResults : (input: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.search_button),
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
                    SearchOptions(genres =  it, navigateToSearchResults = navigateToSearchResults)
                }
            }
        }
    }
}

val searchOptions = mutableStateListOf<Pair<String, String>>()

@Composable
fun SearchOptions(
    genres: List<Genre>,
    navigateToSearchResults: (input: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        searchOptions.add(Pair(first = "include_video", second = "false"))

        itemsIndexed(movieSearchOptionsStrings) { index, option ->
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

                var fieldValue by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = fieldValue,
                    onValueChange = {
                        fieldValue = it.toString()
                        try {
                            searchOptions[index + 1] =
                                searchOptions[index + 1].copy(first = option, second = fieldValue)
                        } catch (e : IndexOutOfBoundsException) {
                            searchOptions.add(Pair(first = option, second = fieldValue))
                        }
                                    }
                )
            }
        }
        item {
            GenresForSearch(genres = genres)
        }
    }

    LazyColumn {
        items(movieSearchOptionsBools) { option ->
            run {
                var boxChecked by remember { mutableStateOf(false) }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = option)
                    Checkbox(
                        checked = boxChecked,
                        onCheckedChange = {
                            boxChecked = !boxChecked
                            try {
                                searchOptions[movieSearchOptionsStrings.size + 3] =
                                    searchOptions[movieSearchOptionsStrings.size + 3]
                                        .copy(first = option, second = boxChecked.toString())
                            } catch (e : IndexOutOfBoundsException) {
                                searchOptions.add(Pair(first = option, second = boxChecked.toString()))
                            }
                        }
                    )
                }
            }
        }
    }

    HorizontalDivider()

    OutlinedButton(
        onClick = {
            navigateToSearchResults(
                searchOptions.map{
                    "${it.first}=${it.second}"
                }.joinToString("&")
            )
        }
    ) {
        Text(text = stringResource(R.string.discover_button))
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
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = { Text("Genres") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            if (genres.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    genres.forEach { selectedGenre ->
                        DropdownMenuItem(
                            text = { Text(selectedGenre.name) },
                            onClick = {
                                textFieldValue = selectedGenre.name
                                menuExpanded = false
                                try {
                                    searchOptions[movieSearchOptionsStrings.size + 2] =
                                        searchOptions[movieSearchOptionsStrings.size + 2]
                                            .copy(first = "with_genres", second = selectedGenre.id.toString())
                                } catch (e : IndexOutOfBoundsException) {
                                    searchOptions.add(Pair(first = "with_genres", second = selectedGenre.id.toString()))
                                }
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
    }
}
