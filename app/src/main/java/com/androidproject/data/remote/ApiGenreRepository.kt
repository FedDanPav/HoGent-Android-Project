package com.androidproject.data.remote

import android.util.Log
import com.androidproject.data.local.dao.GenreDao
import com.androidproject.data.local.entity.toGenre
import com.androidproject.data.remote.dto.genres.toDomainList
import com.androidproject.model.Genre
import com.androidproject.model.toGenreEntity
import com.androidproject.util.Resource

/**
 * The repository handling the genres
 */
interface GenreRepository {
    /**
     * Gets genres
     * @return a [Resource] list of [Genre]
     */
    suspend fun getGenres(): Resource<List<Genre>>
}

/**
 * The implementation of the [GenreRepository]
 * @param tmdbApi the instance of [TheMovieDBApi]
 * @param genreDao the instance of [GenreDao]
 */
class ApiGenreRepository (
    private val tmdbApi : TheMovieDBApi,
    private val genreDao: GenreDao
) : GenreRepository {
    /**
     * The implementation of [GenreRepository.getGenres]
     * @return a [Resource] list of [Genre]
     */
    override suspend fun getGenres(): Resource<List<Genre>> {
        return try {
            val genres = tmdbApi.getMovieGenres().toDomainList()
            try {
                // if that succeeds, store the genres in the local database
                genreDao.upsertGenres(genres.map { it.toGenreEntity() })
            } catch (e: Exception) {
                Log.e("DB-ERROR", "Error while storing genres in local database: ${e.message}")
            }
            Resource.Success(genres)
        } catch (e: Exception) {
            // if the API call fails, try to get the genres from the database
            val genres = genreDao.getGenres().map { it.toGenre() }
            if (genres.isEmpty()) {
                return Resource.Error(e.message ?: "An unknown error occurred")
            }
            Resource.Success(genres)
        }
    }
}
