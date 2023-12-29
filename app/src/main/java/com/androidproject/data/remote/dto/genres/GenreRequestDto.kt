package com.androidproject.data.remote.dto.genres

import com.androidproject.model.Genre
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class GenreRequestDto(
    @JsonProperty("genres") val genres: List<GenreDto>
)
fun GenreRequestDto.toDomainList(): List<Genre> {
    return this.genres.map{ it.toDomainObject() }
}
