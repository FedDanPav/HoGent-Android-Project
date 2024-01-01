package com.androidproject.presentation.screens.savedMovies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.presentation.screens.shared.ResultOverview
import com.androidproject.presentation.screens.shared.ErrorScreen
import com.androidproject.presentation.screens.shared.LoadingScreen
import com.androidproject.util.Resource

/**
 * THe saved movies screen
 * @param paddingValues the padding values to use
 * @param viewModel the view model to use
 */
@Composable
fun SavedMoviesScreen(
    paddingValues : PaddingValues,
    viewModel: SavedMoviesViewModel = viewModel(
        factory = SavedMoviesViewModel.Factory
    )
) {
    val genresState by viewModel.genresUiState.collectAsState()
    val movieState by viewModel.moviesUiState.collectAsState()
    fun handleMovie(movie: Movie) {
        if (movie.isSaved) {
            viewModel.deleteMovie(movie)
        } else {
            viewModel.saveMovie(movie)
        }
    }
    SavedMoviesScreen(
        paddingValues = paddingValues,
        genreUiState = genresState,
        movieUiState = movieState,
        handleMovie = ::handleMovie
    )
}

/**
 * THe saved movies screen
 * @param paddingValues the padding values to use
 * @param genreUiState the UI state of the genres
 * @param movieUiState the UI state of the movies
 * @param handleMovie a method that will either add or delete the movie from the local database
 */
@Composable
fun SavedMoviesScreen(
    paddingValues : PaddingValues,
    genreUiState : Resource<List<Genre>>,
    movieUiState : Resource<List<Movie>>,
    handleMovie: (movie: Movie) -> Unit
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
                                ResultOverview(genres = genreList, movies = it, handleMovie)
                            }
                        }
                    }
                }
            }
        }
    }
}
