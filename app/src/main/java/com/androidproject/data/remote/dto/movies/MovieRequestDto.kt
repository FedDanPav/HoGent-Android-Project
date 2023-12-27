package com.androidproject.data.remote.dto.movies

import com.androidproject.model.Movie
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class MovieRequestDto(
    @JsonProperty("movies") val movies: List<MovieDto>
)
fun MovieRequestDto.toDomainList(): List<Movie> {
    return this.movies.map{ it.toDomainObject() }
}
