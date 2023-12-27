package com.androidproject.presentation.screens.searchResults

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.util.Resource

@Composable
fun SearchResultsScreen(
    paddingValues: PaddingValues,
    viewModel: SearchResultsScreenViewModel = viewModel(
        factory = SearchResultsScreenViewModel.Factory
    )
) {
    val genresState by viewModel.genresUiState.collectAsState()
    val movieState by viewModel.moviesUiState.collectAsState()

    SearchResultsScreen(
        paddingValues = paddingValues,
        genreUiState = genresState,
        movieUiState = movieState
    )
}

fun SearchResultsScreen(
    paddingValues : PaddingValues,
    genreUiState : Resource<List<Genre>>,
    movieUiState : Resource<List<Movie>>
) {

}
