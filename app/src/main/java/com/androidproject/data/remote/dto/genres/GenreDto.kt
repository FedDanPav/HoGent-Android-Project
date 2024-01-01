package com.androidproject.data.remote.dto.genres

import com.androidproject.model.Genre
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

/**
 * A data transfer object for the genres
 * @property id an id used by TMDB
 * @property name the name of the genre
 */
@Serializable
data class GenreDto(
    @JsonProperty("id") val id : Int,
    @JsonProperty("name") val name : String
)

/**
 * A conversion function that converts [GenreDto] to [Genre]
 * @return the converted [Genre]
 */
fun GenreDto.toDomainObject(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
