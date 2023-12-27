package com.androidproject.presentation.screens.searchResults

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.presentation.screens.misc.ErrorScreen
import com.androidproject.presentation.screens.misc.LoadingScreen
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
    LazyHorizontalGrid(
        modifier = Modifier.fillMaxSize(),
        rows = GridCells.Fixed(1)
    ) {
        items(movies) {
            MovieCard(movie = it)
        }
    }
}

@Composable
fun MovieCard(movie: Movie) {
    OutlinedCard {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title
        )

        Text(text = movie.title)

        VerticalDivider()

        Text(text = "Description")
        Text(text = movie.overview)

        VerticalDivider()

        Text(text = "Votes/average")
        Text(text = "${movie.voteCount}/${movie.voteAverage}")
    }
}