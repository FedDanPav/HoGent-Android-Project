package com.androidproject.data.remote

import android.util.Log
import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.dao.MovieToGenreDao
import com.androidproject.data.local.entity.toMovie
import com.androidproject.data.remote.dto.movies.toDomainList
import com.androidproject.model.Movie
import com.androidproject.model.toMovieEntity
import com.androidproject.model.toMovieToGenreEntity
import com.androidproject.util.Resource

interface MovieRepository {
    suspend fun getMovies(args: Map<String, String>): Resource<List<Movie>>
}

class ApiMovieRepository (
    private val tmdbApi : TheMovieDBApi,
    private val movieDao: MovieDao,
    private val movieToGenreDao: MovieToGenreDao
) : MovieRepository {
    override suspend fun getMovies(args: Map<String, String>): Resource<List<Movie>> {
        return try {
            val movies = tmdbApi.getMovies(args).toDomainList()

            try {
                // if that succeeds, store the movies in the local database
                movieDao.upsertMovies(movies.map { it.toMovieEntity() })
                movies.map { it.toMovieToGenreEntity() }.forEach { movieToGenreDao.upsertMoviesToGenres(it) }
            } catch (e: Exception) {
                Log.e("DB-ERROR", "Error while storing movies in local database: ${e.message}")
            }

            Resource.Success(movies)
        } catch (e: Exception) {
            val movies = movieDao.getMovies().map { it.toMovie() }

            movies.forEach{ movie ->
                movie.genreIds =  movieToGenreDao.getGenresByMovieId(movie.id).map { it.genreId }
            }

            if (movies.isEmpty()) {
                return Resource.Error(e.message ?: "An unknown error occurred")
            }
            Resource.Success(movies)
        }
    }
}
