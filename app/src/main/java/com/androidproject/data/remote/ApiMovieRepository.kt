package com.androidproject.data.remote

import com.androidproject.data.remote.dto.movies.toDomainList
import com.androidproject.model.Movie
import com.androidproject.util.Resource

interface MovieRepository {
    suspend fun getMovies(): Resource<List<Movie>>
}

class ApiMovieRepository (
    private val tmdbApi : TheMovieDBApi
) : MovieRepository {
    override suspend fun getMovies(): Resource<List<Movie>> {
        return try {
            val movies = tmdbApi.getMovies().toDomainList()
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}