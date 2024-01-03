package com.androidproject.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidproject.TestData
import com.androidproject.data.local.LocalDatabase
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for the [MovieDao]
 * @property database instance of the [LocalDatabase]
 * @property movieDao the [MovieDao]
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var database: LocalDatabase
    private lateinit var movieDao: MovieDao

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

        movieDao = database.movieDao()
    }

    /**
     * Tears down the tests
     */
    @After
    fun tearDown() {
        database.close()
    }

    /**
     * Tests if the the [MovieEntity] has successfully upserted by [MovieDao]
     */
    @Test
    fun testUpsert() = runTest(UnconfinedTestDispatcher()) {
        movieDao.upsertMovie(TestData.testMovieEntity)

        val result = movieDao.getMovieById(TestData.testMovieEntity.id)

        assert(result.contains(TestData.testMovieEntity))
    }

    /**
     * Tests if the [MovieDao] has successfully deleted a [MovieEntity]
     */
    @Test
    fun testDelete() = runTest(UnconfinedTestDispatcher()) {
        movieDao.upsertMovie(TestData.testMovieEntity)

        movieDao.deleteMovie(TestData.testMovieEntity)

        val result = movieDao.getMovieById(TestData.testMovieEntity.id)

        assert(result.isEmpty())
    }

    /**
     * Tests if the [MovieDao] can get all saved movies from the local database
     */
    @Test
    fun testGetMovies() = runTest(UnconfinedTestDispatcher()) {
        movieDao.upsertMovie(TestData.testMovieEntity)

        val result = movieDao.getMovies()

        assert(result.contains(TestData.testMovieEntity))
    }
}
