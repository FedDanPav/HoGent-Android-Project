package com.androidproject.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.androidproject.data.local.entity.GenreEntity

/**
 * DAO for the genres
 */
@Dao
interface GenreDao {
    /**
     * Upserts a list of genres into the local database
     * @param genres a list of genres to upsert
     */
    @Upsert
    suspend fun upsertGenres(genres : List<GenreEntity>)

    /**
     * Gets all saved genres from the local database
     * @return a list of saved [GenreEntity]
     */
    @Query("SELECT * FROM genres")
    suspend fun getGenres() : List<GenreEntity>

    /**
     * Gets all saved genres from the local database with a given id
     * @param genreId a valid genreId
     * @return a list of saved [GenreEntity] with the provided id
     */
    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getGenreById(genreId : Int) : GenreEntity
}
