package com.androidproject.data.remote

import com.androidproject.data.remote.dto.GenreDto
import com.androidproject.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBApi {

    @GET("discover/movie?")
    suspend fun getMovies(): List<MovieDto>

    @GET("genre/movie/list?")
    suspend fun getMovieGenres(): List<GenreDto>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}
