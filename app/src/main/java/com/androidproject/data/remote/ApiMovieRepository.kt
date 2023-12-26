package com.androidproject.data.remote

import com.androidproject.data.remote.dto.toDomainObject
import com.androidproject.model.Movie
import com.androidproject.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRepository {
    suspend fun getMovies(): Resource<List<Movie>>
}

@Singleton
class ApiMovieRepository @Inject constructor(
    private val tmdbApi : TheMovieDBApi
) : MovieRepository {
    override suspend fun getMovies(): Resource<List<Movie>> {
        return try {
            val movies = tmdbApi.getMovies().map { it.toDomainObject() }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
