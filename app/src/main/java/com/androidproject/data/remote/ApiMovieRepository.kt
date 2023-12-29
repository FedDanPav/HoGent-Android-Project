package com.androidproject.data.remote

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
    suspend fun saveMovie(movie: Movie)
    suspend fun removeMovie(movie: Movie)
    suspend fun getSavedMovies(): Resource<List<Movie>>
}

class ApiMovieRepository (
    private val tmdbApi : TheMovieDBApi,
    private val movieDao: MovieDao,
    private val movieToGenreDao: MovieToGenreDao
) : MovieRepository {
    override suspend fun getMovies(args: Map<String, String>): Resource<List<Movie>> {
        return try {
            val movies = tmdbApi.getMovies(args).toDomainList()

            Resource.Success(movies)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "No connection")
        }
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDao.upsertMovie(movie.toMovieEntity())
        movieToGenreDao.upsertMoviesToGenres(movie.toMovieToGenreEntity())
    }

    override suspend fun removeMovie(movie: Movie) {
        movieToGenreDao.deleteMoviesToGenres(movieToGenreDao.getGenresByMovieId(movie.id))
        movieDao.deleteMovie(movieDao.getMovieById(movie.id).first())
    }

    override suspend fun getSavedMovies(): Resource<List<Movie>> {
        return try {
            val savedMovies = movieDao.getMovies().map { it.toMovie() }

            if (savedMovies.isEmpty()) {
                return Resource.Error("No movies saved")
            }

            savedMovies.forEach{ movie ->
                movie.genreIds =  movieToGenreDao.getGenresByMovieId(movie.id).map { it.genreId }
            }

            Resource.Success(savedMovies)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}
