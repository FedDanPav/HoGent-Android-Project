package com.androidproject.presentation

import androidx.lifecycle.SavedStateHandle
import com.androidproject.TestData
import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.MovieRepository
import com.androidproject.presentation.screens.searchResults.SearchResultsScreenViewModel
import com.androidproject.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchResultsScreenViewModelTest {
    private lateinit var viewModel: SearchResultsScreenViewModel
    private val mockGenreRepository = mockk<GenreRepository>()
    private val mockMovieRepository = mockk<MovieRepository>()

    private val savedStateHandle = SavedStateHandle(mapOf("args" to TestData.testMovieGetArgsString))
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the tests.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchResultsScreenViewModel(savedStateHandle, mockGenreRepository, mockMovieRepository)

        coEvery { mockGenreRepository.getGenres() } returnsMany listOf(
            Resource.Loading(),
            Resource.Success(mockk())
        )

        coEvery { mockMovieRepository.getMovies(any()) } returnsMany listOf(
            Resource.Loading(),
            Resource.Success(mockk())
        )

        coEvery { mockMovieRepository.getSavedMovies() } returnsMany listOf(
            Resource.Loading(),
            Resource.Success(mockk())
        )

        coEvery { mockMovieRepository.saveMovie(TestData.testMovie) } returns Unit
        coEvery { mockMovieRepository.removeMovie(TestData.testMovie) } returns Unit
    }

    /**
     * Tears down the tests.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `load() should call repository getGenres`() = runTest {
        viewModel.load(TestData.testMovieArgsMap)
        advanceUntilIdle()

        coEvery { mockGenreRepository.getGenres() }
        coEvery { mockMovieRepository.getMovies(any()) }
    }

    @Test
    fun `load() should return success state on successful data fetch`() = runTest {
        coEvery { mockGenreRepository.getGenres() } returns Resource.Success(listOf(TestData.testGenre))
        coEvery { mockMovieRepository.getMovies(any()) } returns Resource.Success(listOf(TestData.testMovie))

        viewModel.load(TestData.testMovieArgsMap)
        advanceUntilIdle()

        val genreState = viewModel.genresUiState.value
        val movieState = viewModel.moviesUiState.value

        TestCase.assertTrue(genreState is Resource.Success && genreState.data == listOf(TestData.testGenre))
        TestCase.assertTrue(movieState is Resource.Success && movieState.data == listOf(TestData.testMovie))
    }

    @Test
    fun `load() should return error state on repository failure`() = runTest {
        val errorMessage = "Error fetching"
        coEvery { mockGenreRepository.getGenres() } returns Resource.Error(errorMessage)
        coEvery { mockMovieRepository.getMovies(any()) } returns Resource.Error(errorMessage)

        viewModel.load(TestData.testMovieArgsMap)
        advanceUntilIdle()

        val genreState = viewModel.genresUiState.value
        val movieState = viewModel.moviesUiState.value

        TestCase.assertTrue(genreState is Resource.Error && genreState.message == errorMessage)
        TestCase.assertTrue(movieState is Resource.Error && movieState.message == errorMessage)
    }

    @Test
    fun `saveMovie() saves the required movie and deleteMovie() deletes it`() = runTest {
        viewModel.saveMovie(TestData.testMovie)
        advanceUntilIdle()

        coVerify { mockMovieRepository.saveMovie(TestData.testMovie) }

        viewModel.deleteMovie(TestData.testMovie)
        advanceUntilIdle()

        coVerify { mockMovieRepository.removeMovie(TestData.testMovie) }
    }
}
