package com.androidproject.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.androidproject.TestData
import com.androidproject.data.local.LocalDatabase
import com.androidproject.data.local.dao.GenreDao
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.entity.GenreEntity
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
 * @property genreDao the [GenreDao]
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GenreDaoTest {
    private lateinit var database: LocalDatabase
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
     * Tests if the the [GenreEntity] has successfully upserted by [GenreDao]
     */
    @Test
    fun testUpsert() = runTest(UnconfinedTestDispatcher()) {
        genreDao.upsertGenres(listOf(TestData.testGenreEntity))

        val result = genreDao.getGenres()

        assert(result.contains(TestData.testGenreEntity))
    }

    /**
     * Tests if the [GenreDao] can get a saved movie by id from the local database
     */
    @Test
    fun testGetMovies() = runTest(UnconfinedTestDispatcher()) {
        genreDao.upsertGenres(listOf(TestData.testGenreEntity))

        val result = genreDao.getGenreById(TestData.testGenreEntity.id)

        assert(result.id == TestData.testGenreEntity.id)
    }
}
