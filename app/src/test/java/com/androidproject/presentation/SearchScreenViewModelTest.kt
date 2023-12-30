package com.androidproject.presentation

import com.androidproject.TestData
import com.androidproject.data.remote.GenreRepository
import com.androidproject.presentation.screens.search.SearchScreenViewModel
import com.androidproject.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
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
class SearchScreenViewModelTest {
    private lateinit var viewModel: SearchScreenViewModel
    private val mockGenreRepository = mockk<GenreRepository>()

    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the tests.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchScreenViewModel(mockGenreRepository)
        coEvery { mockGenreRepository.getGenres() } returnsMany listOf(
            Resource.Loading(),
            Resource.Success(mockk())
        )
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
    fun `initial state should be Loading`() = runTest {
        val initialState = viewModel.genresUiState.value
        TestCase.assertTrue(initialState is Resource.Loading)
    }


    @Test
    fun `loadGenres() should call repository getGenres`() = runTest {
        viewModel.loadGenres()
        advanceUntilIdle()

        coEvery { mockGenreRepository.getGenres() }
    }

    @Test
    fun `loadGenres() should return success state on successful data fetch`() = runTest {
        coEvery { mockGenreRepository.getGenres() } returns Resource.Success(listOf(TestData.testGenre))

        viewModel.loadGenres()
        advanceUntilIdle()

        val state = viewModel.genresUiState.value
        TestCase.assertTrue(state is Resource.Success && state.data == listOf(TestData.testGenre))
    }

    @Test
    fun `getGenres() should return error state on repository failure`() = runTest {
        val errorMessage = "Error fetching posts"
        coEvery { mockGenreRepository.getGenres() } returns Resource.Error(errorMessage)

        viewModel.loadGenres()
        advanceUntilIdle()

        val state = viewModel.genresUiState.value
        TestCase.assertTrue(state is Resource.Error && state.message == errorMessage)
    }
}
