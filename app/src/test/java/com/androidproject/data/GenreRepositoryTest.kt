package com.androidproject.data

import com.androidproject.TestData
import com.androidproject.data.local.dao.GenreDao
import com.androidproject.data.remote.ApiGenreRepository
import com.androidproject.data.remote.GenreRepository
import com.androidproject.data.remote.TheMovieDBApi
import com.androidproject.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
class GenreRepositoryTest {
    private val mockApi = mockk<TheMovieDBApi>()
    private val mockGenreDao = mockk<GenreDao>()
    private lateinit var genreRepository: GenreRepository
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the tests.
     */
    @Before
    fun setUp() {
        genreRepository = ApiGenreRepository(mockApi, mockGenreDao)
        coEvery { mockGenreDao.upsertGenres(any()) } returns Unit
        Dispatchers.setMain(testDispatcher)
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
    fun `getGenres returns success with valid data from the API`() = runTest {
        coEvery { mockApi.getMovieGenres() } returns TestData.testGenreRequestDto
        coEvery { mockGenreDao.getGenres() } returns listOf(TestData.testGenreEntity)

        val result = genreRepository.getGenres()
        advanceUntilIdle()

        assertTrue(result is Resource.Success)
        assertEquals(TestData.testGenre, result.data!!.first())

        coVerify { mockGenreDao.upsertGenres(any()) }
    }

    @Test
    fun `getGenres returns data from the database when API fails to respond`() = runTest {
        coEvery { mockApi.getMovieGenres() } throws Exception("API Error")
        coEvery { mockGenreDao.getGenres() } returns listOf(TestData.testGenreEntity)

        val result = genreRepository.getGenres()
        advanceUntilIdle()

        assertTrue(result is Resource.Success)
        assertEquals(TestData.testGenre, result.data!!.first())
    }
}
