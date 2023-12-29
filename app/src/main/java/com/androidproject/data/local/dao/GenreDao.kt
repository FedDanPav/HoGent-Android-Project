package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.GenreEntity

@Dao
interface GenreDao {
    @Upsert
    suspend fun upsertGenres(genres : List<GenreEntity>)

    @Query("SELECT * FROM genres")
    suspend fun getGenres() : List<GenreEntity>

    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getGenreById(genreId : Int) : GenreEntity
}
