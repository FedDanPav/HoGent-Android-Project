package com.androidproject.presentation.screens.searchResults

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.presentation.screens.misc.ErrorScreen
import com.androidproject.presentation.screens.misc.LoadingScreen
import com.androidproject.util.Resource
import java.lang.NullPointerException

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

@Composable
fun SearchResultsScreen(
    paddingValues : PaddingValues,
    genreUiState : Resource<List<Genre>>,
    movieUiState : Resource<List<Movie>>
) {
    Column (

        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
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
                genreUiState.data?.let { genreList ->
                    when (movieUiState) {
                        is Resource.Loading -> {
                            LoadingScreen()
                        }

                        is Resource.Error -> {
                            ErrorScreen(
                                error = movieUiState.message ?: stringResource(R.string.unknownerror)
                            )
                        }

                        is Resource.Success -> {
                            movieUiState.data?.let {
                                ResultOverview(genres = genreList, movies = it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultOverview(
    genres : List<Genre>,
    movies : List<Movie>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 88.dp, start = 12.dp, end = 12.dp)
    ) {
        items(movies) {
            MovieCard(movie = it, genres = genres)
        }

        item {
            OutlinedButton(
                onClick = {  }
            ) {
                Text(text = "Next page")
            }
        }

        item {
            OutlinedButton(
                onClick = {  }
            ) {
                Text(text = "Previous page")
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, genres: List<Genre>) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.47F)
    ) {
        var genresInMovie = ""

        try {
            genresInMovie =  genres.filter {
                movie.genreIds!!.contains(it.id)
            }.map { it.name }.joinToString()
        } catch (e: NullPointerException) {
            Text(text = "No internet connection")
        }

        if (genresInMovie.isNotBlank()) {
            Text(text = movie.title)

            VerticalDivider()

            Text(text = "Description")
            Text(text = movie.overview)

            VerticalDivider()

            Text(text = "Votes/average")
            Text(text = "${movie.voteCount}/${movie.voteAverage}")

            VerticalDivider()

            Text(text = "Genres: $genresInMovie")
        }
    }
}
