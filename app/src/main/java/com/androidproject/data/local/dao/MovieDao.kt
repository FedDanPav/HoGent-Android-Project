package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.MovieEntity

/**
 * DAO for the saved movies
 */
@Dao
interface MovieDao {
    /**
     * Upserts a provided movie to the local database
     * @param movie a valid [MovieEntity]
     */
    @Upsert
    suspend fun upsertMovie(movie: MovieEntity)

    /**
     * Deletes a provided movie from the local database
     * @param movie a valid [MovieEntity]
     */
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    /**
     * Gets all saved movies from the local database
     * @return a list of saved [MovieEntity]
     */
    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieEntity>

    /**
     * Gets all saved movies from the local database with a given id
     * @param movieId a valid movieId
     * @return a list of saved [MovieEntity] with the provided id
     */
    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    suspend fun getMovieById(movieId : Int) : List<MovieEntity>
}
