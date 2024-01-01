package com.androidproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidproject.model.Genre

/**
 * A database entity for the genres
 * @property id an id used by the TMDB to find and identify genres
 * @property name a name of the genre
 */
@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey @ColumnInfo(name = "genreId") val id: Int,
    val name: String
)

/**
 * Converts [GenreEntity] into [Genre]
 * @return the converted [Genre]
 */
fun GenreEntity.toGenre() : Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
