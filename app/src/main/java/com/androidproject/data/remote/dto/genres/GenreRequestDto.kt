package com.androidproject.data.remote.dto.genres

import com.androidproject.model.Genre
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

/**
 * A data transfer object for the request to the TMDB API
 * @property genres a list of genres which come with the request
 */
@Serializable
data class GenreRequestDto(
    @JsonProperty("genres") val genres: List<GenreDto>
)

/**
 * A conversion function to convert [GenreRequestDto] to a list of [Genre]
 * @return list of [Genre]
 */
fun GenreRequestDto.toDomainList(): List<Genre> {
    return this.genres.map{ it.toDomainObject() }
}
