package com.androidproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidproject.model.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey @ColumnInfo(name = "genreId") val id: Int,
    val name: String
)

fun GenreEntity.toGenre() : Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}
