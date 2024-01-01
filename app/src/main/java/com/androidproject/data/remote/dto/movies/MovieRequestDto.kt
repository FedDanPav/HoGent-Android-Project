package com.androidproject.data.remote.dto.movies

import com.androidproject.model.Movie
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

/**
 * A data transfer object for the request to the TMDB API
 * @property pageNum the number of the current page
 * @property results a list of movies which come with the request
 * @property totalPages the total amount of pages
 * @property totalResults the total amount of results
 */
@Serializable
data class MovieRequestDto(
    @JsonProperty("page") val pageNum: Int,
    @JsonProperty("results") val results: List<MovieDto>,
    @JsonProperty("total_pages") val totalPages: Int,
    @JsonProperty("total_results") val totalResults: Int
)

/**
 * A conversion function to convert [MovieRequestDto] to a list of [Movie]
 * @return list of [Movie]
 */
fun MovieRequestDto.toDomainList(): List<Movie> {
    return this.results.map{ it.toDomainObject() }
}
