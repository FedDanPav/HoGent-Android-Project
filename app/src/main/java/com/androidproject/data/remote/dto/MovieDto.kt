package com.androidproject.data.remote.dto

import com.androidproject.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val title : String,
    val overview : String,
    val posterPath : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int
)

fun MovieDto.toDomainObject(): Movie {
    return Movie(
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}
