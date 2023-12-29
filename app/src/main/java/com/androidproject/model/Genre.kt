package com.androidproject.model

import com.androidproject.data.local.entity.GenreEntity

data class Genre(
    val id : Int,
    val name : String
)

fun Genre.toGenreEntity() : GenreEntity {
    return GenreEntity(
        id = this.id,
        name = this.name
    )
}
