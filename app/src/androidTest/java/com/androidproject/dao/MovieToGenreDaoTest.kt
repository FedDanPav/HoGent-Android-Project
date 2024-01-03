package com.androidproject.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidproject.TestData
import com.androidproject.data.local.LocalDatabase
import com.androidproject.data.local.dao.GenreDao
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.dao.MovieToGenreDao
import com.androidproject.data.local.entity.MovieToGenreEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for the [MovieToGenreDao]
 * @property database instance of the [LocalDatabase]
 * @property mtgd the [MovieToGenreDao] which will be tested
 * @property movieDao the [MovieDao]
 * @property genreDao the [GenreDao]
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MovieToGenreDaoTest {
    private lateinit var database: LocalDatabase
    private lateinit var mtgd: MovieToGenreDao
    private lateinit var movieDao: MovieDao
    private lateinit var genreDao: GenreDao

    /**
     * Sets up the tests
     */
    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        mtgd = database.movieToGenreDao()
        movieDao = database.movieDao()
        genreDao = database.genreDao()
    }

    /**
     * Tears down the tests
     */
    @After
    fun tearDown() {
        database.close()
    }

    /**
     * Tests if the the [MovieToGenreEntity] has successfully upserted by [MovieToGenreDao]
     */
    @Test
    fun testUpsert() = runTest(UnconfinedTestDispatcher()) {
        movieDao.upsertMovie(TestData.testMovieEntity)
        genreDao.upsertGenres(listOf(TestData.testGenreEntity))
        mtgd.upsertMoviesToGenres(listOf(TestData.testMovieToGenreEntity))

        val result = mtgd.getGenresByMovieId(TestData.testMovieToGenreEntity.movieId)

        assert(result.contains(TestData.testMovieToGenreEntity))
    }

    /**
     * Tests if the [MovieToGenreDao] has successfully deleted a [MovieToGenreEntity]
     */
    @Test
    fun testDelete() = runTest(UnconfinedTestDispatcher()) {
        movieDao.upsertMovie(TestData.testMovieEntity)
        genreDao.upsertGenres(listOf(TestData.testGenreEntity))
        mtgd.upsertMoviesToGenres(listOf(TestData.testMovieToGenreEntity))

        mtgd.deleteMoviesToGenres(listOf(TestData.testMovieToGenreEntity))

        val result = mtgd.getGenresByMovieId(TestData.testMovieToGenreEntity.movieId)

        assert(result.isEmpty())
    }
}
