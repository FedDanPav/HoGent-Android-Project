package com.androidproject.data.remote

import com.androidproject.data.remote.dto.genres.GenreRequestDto
import com.androidproject.data.remote.dto.movies.MovieRequestDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface TheMovieDBApi {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMTVjMjM2MzBkNTYyM2ZmMzlhNzE1ZjM0NGYyNDllOCIsInN1YiI6IjY1NWI0MjFkZjY3ODdhMDEzYTViZGU0MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JA9QlnTVbeN5cTihYMNldyb_AdhTfoay2-Ts5fSbcz0")
    @GET("discover/movie?include_video=false")
    suspend fun getMovies(): MovieRequestDto

    @Headers("Accept: application/json","Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMTVjMjM2MzBkNTYyM2ZmMzlhNzE1ZjM0NGYyNDllOCIsInN1YiI6IjY1NWI0MjFkZjY3ODdhMDEzYTViZGU0MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JA9QlnTVbeN5cTihYMNldyb_AdhTfoay2-Ts5fSbcz0")
    @GET("genre/movie/list?")
    suspend fun getMovieGenres(): GenreRequestDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}
