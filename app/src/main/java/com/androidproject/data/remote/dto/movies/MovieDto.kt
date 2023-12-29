package com.androidproject.data.remote.dto.movies

import com.androidproject.model.Movie
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class MovieDto(
    @JsonProperty("title") val title : String,
    @JsonProperty("overview") val overview : String,
    @JsonProperty("original_language") val originalLanguage : String,
    @JsonProperty("vote_average") val voteAverage : Float,
    @JsonProperty("vote_count") val voteCount : Int,
    @JsonProperty("genre_ids") val genreIds : List<Int>
)

fun MovieDto.toDomainObject(): Movie {
    return Movie(
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genreIds = this.genreIds
    )
}
