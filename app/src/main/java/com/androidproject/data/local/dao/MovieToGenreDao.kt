package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.MovieToGenreEntity

/**
 * DAO for the many-to-many relation between movies and genres
 */
@Dao
interface MovieToGenreDao {
    /**
     * Upserts a many-to-many relation between movies and genres
     * @param mte a list of [MovieToGenreEntity]
     */
    @Upsert
    suspend fun upsertMoviesToGenres(mte : List<MovieToGenreEntity>)

    /**
     * Delete a many-to-many relation between movies and genres
     * @param mte a list of [MovieToGenreEntity]
     */
    @Delete
    suspend fun deleteMoviesToGenres(mte : List<MovieToGenreEntity>)

    /**
     * Gets a list of many-to-many relations between movies and genres
     * @param movieId a valid movieId
     * @return a list of [MovieToGenreEntity]
     */
    @Query("SELECT * FROM movies_to_genres WHERE tbl_movieId = :movieId")
    suspend fun getGenresByMovieId(movieId : Int) : List<MovieToGenreEntity>
}
