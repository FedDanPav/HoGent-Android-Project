package com.androidproject.data.remote

import com.androidproject.data.remote.dto.toDomainObject
import com.androidproject.model.Genre
import com.androidproject.util.Resource
import javax.inject.Inject

interface GenreRepository {
    suspend fun getGenres(): Resource<List<Genre>>
}

class ApiGenreRepository @Inject constructor(
    private val tmdbApi : TheMovieDBApi
) : GenreRepository {
    override suspend fun getGenres(): Resource<List<Genre>> {
        return try {
            val genres = tmdbApi.getMovieGenres().map { it.toDomainObject() }
            Resource.Success(genres)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

}
