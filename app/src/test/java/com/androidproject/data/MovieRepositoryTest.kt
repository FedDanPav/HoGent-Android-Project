package com.androidproject.data

import com.androidproject.TestData
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.dao.MovieToGenreDao
import com.androidproject.data.remote.ApiMovieRepository
import com.androidproject.data.remote.MovieRepository
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

/**
 * Tests for the [MovieRepository]
 * @property mockApi the mocked [TheMovieDBApi]
 * @property mockMovieDao the mocked [MovieDao]
 * @property mockMovieToGenreDao the mocked [MovieToGenreDao]
 * @property movieRepository the mocked [MovieRepository] which will be tested
 * @property testDispatcher the [StandardTestDispatcher]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {
    private val mockApi = mockk<TheMovieDBApi>()
    private val mockMovieDao = mockk<MovieDao>()
    private val mockMovieToGenreDao = mockk<MovieToGenreDao>()
    private lateinit var movieRepository: MovieRepository
    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the tests.
     */
    @Before
    fun setUp() {
        movieRepository = ApiMovieRepository(mockApi, mockMovieDao, mockMovieToGenreDao)
        coEvery { mockMovieDao.upsertMovie(any()) } returns Unit
        coEvery { mockMovieDao.deleteMovie(any()) } returns Unit

        coEvery { mockMovieToGenreDao.upsertMoviesToGenres(any()) } returns Unit
        coEvery { mockMovieToGenreDao.deleteMoviesToGenres(any()) } returns Unit

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

    /**
     * Tests if the [MovieRepository.getMovies] returns [Resource.Success] when the API
     * call returns valid data
     */
    @Test
    fun `getMovies returns success with valid data from API`() = runTest {
        coEvery { mockApi.getMovies(TestData.testMovieArgsMap) } returns TestData.testMovieRequestDto

        val result = movieRepository.getMovies(TestData.testMovieArgsMap)
        advanceUntilIdle()

        assertTrue(result is Resource.Success)
        assertEquals(listOf(TestData.testMovie), result.data)
    }

    /**
     * Tests if the [MovieRepository.getMovies] returns [Resource.Error] when the API call fails
     */
    @Test
    fun `getMovies returns an error due to API fail`() = runTest {
        coEvery { mockApi.getMovies(TestData.testMovieArgsMap) } throws Exception("API Error")

        val result = movieRepository.getMovies(TestData.testMovieArgsMap)
        advanceUntilIdle()

        assertTrue(result is Resource.Error)
    }

    /**
     * Tests if the [MovieRepository] can sucessfully add and remove a movie from
     * the local database
     */
    @Test
    fun `saveMovie upserts the required movie to the database and then deletes it`() = runTest {
        coEvery { mockMovieToGenreDao.getGenresByMovieId(any()) } returns TestData.testMovieToGenreEntities
        coEvery { mockMovieDao.getMovieById(any()) } returns listOf(TestData.testMovieEntity)

        movieRepository.saveMovie(TestData.testMovie)
        advanceUntilIdle()

        coVerify { mockMovieDao.upsertMovie(TestData.testMovieEntity) }
        coVerify { mockMovieToGenreDao.upsertMoviesToGenres(TestData.testMovieToGenreEntities) }

        movieRepository.removeMovie(TestData.testMovie)
        advanceUntilIdle()

        coVerify { mockMovieDao.deleteMovie(TestData.testMovieEntity) }
        coVerify { mockMovieToGenreDao.deleteMoviesToGenres(TestData.testMovieToGenreEntities) }
    }

    /**
     * Tests if [MovieRepository.getSavedMovies] returns the saved movie from the local database
     */
    @Test
    fun `getSavedMovies returns the saved movies from the database`() = runTest {
        coEvery { mockMovieDao.getMovies() } returns listOf(TestData.testMovieEntity)
        coEvery { mockMovieToGenreDao.getGenresByMovieId(any()) } returns TestData.testMovieToGenreEntities
        coEvery { mockMovieDao.getMovieById(any()) } returns listOf(TestData.testMovieEntity)

        movieRepository.saveMovie(TestData.testMovie)
        val result = movieRepository.getSavedMovies()
        advanceUntilIdle()

        assertTrue(result is Resource.Success)
        assertEquals(TestData.testMovieSaved, result.data!!.first())
    }

    /**
     * Tests if the [MovieRepository.getSavedMovies] returns an [Resource.Error] if the the list
     * from the local database returns empty
     */
    @Test
    fun `getSavedMovies returns an empty list from the database`() = runTest {
        coEvery { mockMovieDao.getMovies() } returns emptyList()

        val result = movieRepository.getSavedMovies()
        advanceUntilIdle()

        assertTrue(result is Resource.Error)
    }
}
