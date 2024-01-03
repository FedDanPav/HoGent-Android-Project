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

/**
 * Tests for the [SearchScreenViewModel]
 * @property viewModel the mocked [SearchScreenViewModel] which will be tested
 * @property mockGenreRepository the mocked [GenreRepository]
 * @property testDispatcher the [StandardTestDispatcher]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenViewModelTest {
    private lateinit var viewModel: SearchScreenViewModel
    private val mockGenreRepository = mockk<GenreRepository>()

    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the tests
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
     * Tears down the tests
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    /**
     * Tests if the initial state of [SearchScreenViewModel] is [Resource.Loading]
     */
    @Test
    fun `initial state should be Loading`() = runTest {
        val initialState = viewModel.genresUiState.value
        TestCase.assertTrue(initialState is Resource.Loading)
    }

    /**
     * Tests if the [SearchScreenViewModel.loadGenres] calls [GenreRepository.getGenres]
     */
    @Test
    fun `loadGenres() should call repository getGenres`() = runTest {
        viewModel.loadGenres()
        advanceUntilIdle()

        coEvery { mockGenreRepository.getGenres() }
    }

    /**
     * Tests if the [SearchScreenViewModel.loadGenres] returns [Resource.Success] when the
     * repository call is successful
     */
    @Test
    fun `loadGenres() should return success state on successful data fetch`() = runTest {
        coEvery { mockGenreRepository.getGenres() } returns Resource.Success(listOf(TestData.testGenre))

        viewModel.loadGenres()
        advanceUntilIdle()

        val state = viewModel.genresUiState.value
        TestCase.assertTrue(state is Resource.Success && state.data == listOf(TestData.testGenre))
    }

    /**
     * Tests if the [SearchScreenViewModel.loadGenres] returns [Resource.Error] when the
     * repository call fails
     */
    @Test
    fun `loadGenres() should return error state on repository failure`() = runTest {
        val errorMessage = "Error fetching genres"
        coEvery { mockGenreRepository.getGenres() } returns Resource.Error(errorMessage)

        viewModel.loadGenres()
        advanceUntilIdle()

        val state = viewModel.genresUiState.value
        TestCase.assertTrue(state is Resource.Error && state.message == errorMessage)
    }
}
