package com.androidproject.model

import com.androidproject.data.local.entity.GenreEntity

/**
 * Data class for the genres
 * @property id an id used by the TMDB to find and identify genres
 * @property name a name of the genre
 */
data class Genre(
    val id : Int,
    val name : String
)

/**
 * Converts [Genre] into [GenreEntity]
 * @return the converted [GenreEntity]
 */
fun Genre.toGenreEntity() : GenreEntity {
    return GenreEntity(
        id = this.id,
        name = this.name
    )
}
