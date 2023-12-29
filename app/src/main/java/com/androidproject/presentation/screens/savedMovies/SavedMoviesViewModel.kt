package com.androidproject.presentation.screens.savedMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.androidproject.MainApplication
import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.MovieRepository
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedMoviesViewModel (
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _genres = MutableStateFlow<Resource<List<Genre>>>(
        Resource.Loading()
    )

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(
        Resource.Loading()
    )

    val genresUiState : StateFlow<Resource<List<Genre>>> = _genres.asStateFlow()
    val moviesUiState : StateFlow<Resource<List<Movie>>> = _movies.asStateFlow()


    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val genresResource = genreRepository.getGenres()
            _genres.value = genresResource

            val movieResource = movieRepository.getSavedMovies()
            _movies.value = movieResource
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (
                            this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                                    as MainApplication
                            )
                val genreRepository = application.container.apiGenreRepository
                val movieRepository = application.container.apiMovieRepository

                SavedMoviesViewModel(
                    genreRepository, movieRepository
                )
            }
        }
    }
}
