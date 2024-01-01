package com.androidproject.data.remote

import com.androidproject.data.local.dao.MovieDao
import com.androidproject.data.local.dao.MovieToGenreDao
import com.androidproject.data.local.entity.toMovie
import com.androidproject.data.remote.dto.movies.toDomainList
import com.androidproject.model.Movie
import com.androidproject.model.toMovieEntity
import com.androidproject.model.toMovieToGenreEntity
import com.androidproject.util.Resource

/**
 * The repository handling the movies
 */
interface MovieRepository {
    /**
     * Gets genres
     * @param args [Map] of search arguments from the user-selected options
     * @return a [Resource] list of [Movie]
     */
    suspend fun getMovies(args: Map<String, String>): Resource<List<Movie>>

    /**
     * Saves a provided movie to the local database
     * @param movie a valid [Movie]
     */
    suspend fun saveMovie(movie: Movie)

    /**
     * Deletes a provided movie from the local database
     * @param movie a valid [Movie]
     */
    suspend fun removeMovie(movie: Movie)

    /**
     * Gets all saved movies from the local database
     * @return a [Resource] list of saved [Movie]
     */
    suspend fun getSavedMovies(): Resource<List<Movie>>
}

/**
 * The implementation of the [MovieRepository]
 * @param tmdbApi the instance of [TheMovieDBApi]
 * @param movieDao the instance of [MovieDao]
 * @param movieToGenreDao the instance of [MovieToGenreDao]
 */
class ApiMovieRepository (
    private val tmdbApi : TheMovieDBApi,
    private val movieDao: MovieDao,
    private val movieToGenreDao: MovieToGenreDao
) : MovieRepository {
    /**
     * Implementation of [MovieRepository.getMovies]
     * @param args [Map] of search arguments from the user-selected options
     * @return a [Resource] list of [Movie]
     */
    override suspend fun getMovies(args: Map<String, String>): Resource<List<Movie>> {
        return try {
            val movies = tmdbApi.getMovies(args).toDomainList()

            Resource.Success(movies)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "No connection")
        }
    }

    /**
     * Implementation of [MovieRepository.saveMovie]
     * @param movie a valid [Movie]
     */
    override suspend fun saveMovie(movie: Movie) {
        movieDao.upsertMovie(movie.toMovieEntity())
        movieToGenreDao.upsertMoviesToGenres(movie.toMovieToGenreEntity())
    }

    /**
     * Implementation of [MovieRepository.removeMovie]
     * @param movie a valid [Movie]
     */
    override suspend fun removeMovie(movie: Movie) {
        movieToGenreDao.deleteMoviesToGenres(movieToGenreDao.getGenresByMovieId(movie.id))
        movieDao.deleteMovie(movieDao.getMovieById(movie.id).first())
    }

    /**
     * Implementation of [MovieRepository.getSavedMovies]
     * @return a [Resource] list of saved [Movie]
     */
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
