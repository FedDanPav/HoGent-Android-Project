package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.MovieToGenreEntity

@Dao
interface MovieToGenreDao {
    @Upsert
    suspend fun upsertMoviesToGenres(mte : List<MovieToGenreEntity>)

    @Delete
    suspend fun deleteMoviesToGenres(mte : List<MovieToGenreEntity>)

    @Query("SELECT * FROM movies_to_genres WHERE tbl_movieId = :movieId")
    suspend fun getGenresByMovieId(movieId : Int) : List<MovieToGenreEntity>
}
