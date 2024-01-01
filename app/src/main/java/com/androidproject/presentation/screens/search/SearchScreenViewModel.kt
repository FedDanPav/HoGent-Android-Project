package com.androidproject.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.androidproject.MainApplication
import com.androidproject.data.remote.GenreRepository
import com.androidproject.model.Genre
import com.androidproject.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * he view model for [SearchScreen]
 * @param genreRepository the [GenreRepository] to retrieve required data
 */
class SearchScreenViewModel (
    private val genreRepository: GenreRepository,
) : ViewModel()  {
    private val _genres = MutableStateFlow<Resource<List<Genre>>>(
        Resource.Loading()
    )
    val genresUiState : StateFlow<Resource<List<Genre>>> = _genres.asStateFlow()

    init {
        loadGenres()
    }

    /**
     * Loads the needed information
     */
    fun loadGenres() {
        viewModelScope.launch {
            val genresResource = genreRepository.getGenres()
            _genres.value = genresResource
        }
    }

    /**
     * Factory object for [SearchScreenViewModel]
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (
                            this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                                    as MainApplication
                    )
                val genreRepository = application.container.apiGenreRepository
                SearchScreenViewModel(
                    genreRepository
                )
            }
        }
    }
}
