package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.MovieToGenreEntity

@Dao
interface MovieToGenreDao {
    @Upsert
    suspend fun upsertMoviesToDiseases(mte : List<MovieToGenreEntity>)

    @Query("SELECT * FROM movies_to_genres WHERE tbl_movieId = :movieId")
    suspend fun getGenresByMovieId(movieId : Int) : MovieToGenreEntity
}
