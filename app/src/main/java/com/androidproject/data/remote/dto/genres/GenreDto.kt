package com.androidproject.data.remote.dto.genres

import com.androidproject.model.Genre
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @JsonProperty("id") val id : Int,
    @JsonProperty("name") val name : String
)

fun GenreDto.toDomainObject(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
