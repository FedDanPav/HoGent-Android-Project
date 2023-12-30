package com.androidproject.model

import com.androidproject.data.local.entity.MovieEntity
import com.androidproject.data.local.entity.MovieToGenreEntity

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
