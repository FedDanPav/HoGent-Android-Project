package com.androidproject.data.remote.dto.movies

import com.androidproject.model.Movie
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class MovieRequestDto(
    @JsonProperty("page") val pageNum: Int,
    @JsonProperty("results") val results: List<MovieDto>,
    @JsonProperty("total_pages") val totalPages: Int,
    @JsonProperty("total_results") val totalResults: Int
)
fun MovieRequestDto.toDomainList(): List<Movie> {
    return this.results.map{ it.toDomainObject() }
}
