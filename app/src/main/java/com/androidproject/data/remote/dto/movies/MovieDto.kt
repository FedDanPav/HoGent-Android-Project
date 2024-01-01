package com.androidproject.data.remote.dto.movies

import com.androidproject.model.Movie
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

/**
 * A data transfer object for the genres
 * @property id an id used by TMDB
 * @property title title of the movie
 * @property overview a description of the movie
 * @property originalLanguage the original language of the movie
 * @property voteAverage the average vote
 * @property voteCount the amount of people who shared a vote on the movie
 * @property genreIds a list of genres this movie has a relation to
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class MovieDto(
    @JsonProperty("id") val id : Int,
    @JsonProperty("title") val title : String,
    @JsonProperty("overview") val overview : String,
    @JsonProperty("original_language") val originalLanguage : String,
    @JsonProperty("vote_average") val voteAverage : Float,
    @JsonProperty("vote_count") val voteCount : Int,
    @JsonProperty("genre_ids") val genreIds : List<Int>
)

/**
 * A conversion function that converts [MovieDto] to [Movie]
 * @return the converted [Movie]
 */
fun MovieDto.toDomainObject(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genreIds = this.genreIds
    )
}
