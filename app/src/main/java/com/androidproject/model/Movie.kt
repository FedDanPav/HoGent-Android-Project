package com.androidproject.model

import com.androidproject.data.local.entity.MovieEntity

data class Movie(
    val title : String,
    val overview : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int,
    val genreIds : List<Int>?
)

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = 0,
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
