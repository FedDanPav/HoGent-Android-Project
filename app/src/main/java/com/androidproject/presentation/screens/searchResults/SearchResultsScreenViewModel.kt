package com.androidproject.presentation.screens.searchResults

import androidx.lifecycle.SavedStateHandle
import com.androidproject.data.remote.MovieRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.androidproject.MainApplication
import com.androidproject.data.remote.GenreRepository
import com.androidproject.model.Genre
import com.androidproject.model.Movie
import com.androidproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchResultsScreenViewModel (
    savedStateHandle: SavedStateHandle,
    private val genreRepository: GenreRepository,
    private val movieRepository: MovieRepository
) : ViewModel()  {
    private val _genres = MutableStateFlow<Resource<List<Genre>>>(
        Resource.Loading()
    )

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(
        Resource.Loading()
    )

    val genresUiState : StateFlow<Resource<List<Genre>>> = _genres.asStateFlow()
    val moviesUiState : StateFlow<Resource<List<Movie>>> = _movies.asStateFlow()
    fun saveMovie(input: Movie) {
        viewModelScope.launch {
            movieRepository.saveMovie(input)
        }
    }

    private val args : String = checkNotNull(savedStateHandle["args"])
    private val argsMap = args.split("&").associate {
        val (key, value) = it.split("=")
        key to value
    }

    init {
        load(argsMap)
    }

    private fun load(args : Map<String, String>) {
        viewModelScope.launch {
            val genresResource = genreRepository.getGenres()
            _genres.value = genresResource

            val movieResource = movieRepository.getMovies(args)
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

                SearchResultsScreenViewModel(
                    this.createSavedStateHandle(), genreRepository, movieRepository
                )
            }
        }
    }
}
