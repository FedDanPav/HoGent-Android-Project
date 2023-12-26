package com.androidproject.data.remote.dto

import com.androidproject.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id : Int,
    val name : String
)

fun GenreDto.toDomainObject(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
