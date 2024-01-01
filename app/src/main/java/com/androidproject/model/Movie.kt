package com.androidproject.model

import com.androidproject.data.local.entity.MovieEntity
import com.androidproject.data.local.entity.MovieToGenreEntity

/**
 * Data class for the movies
 * @property id a movieId used by the TMDB to find and identify the movie
 * @property title title of the movie
 * @property overview a description of the movie
 * @property originalLanguage the original language of the movie
 * @property voteAverage the average vote
 * @property voteCount the amount of people who shared a vote on the movie
 * @property genreIds a list of genres this movie has a relation to
 * @property isSaved a boolean to check whenever or not the movie is save locally
 */
data class Movie(
    val id : Int,
    val title : String,
    val overview : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int,
    var genreIds : List<Int>?,
    var isSaved : Boolean = false
)

/**
 * Converts the [Movie] into a [MovieEntity]
 * @return the converted [MovieEntity]
 */
fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
/**
 * Converts the [Movie] into a list of [MovieToGenreEntity]
 * @return a list of the converted [MovieToGenreEntity]
 */
fun Movie.toMovieToGenreEntity(): List<MovieToGenreEntity> {
    val movieAndGenres = mutableListOf<MovieToGenreEntity>()

    if (genreIds != null) {
        for (genreId in genreIds!!) {
            movieAndGenres.add(
                MovieToGenreEntity(
                    movieId = this.id,
                    genreId
                )
            )
        }
    }

    return movieAndGenres
}
