package com.androidproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies_to_genres",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf("movieId"),
            childColumns = arrayOf("tbl_movieId")
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = arrayOf("genreId"),
            childColumns = arrayOf("tbl_genreId")
        )
    ]
)
data class MovieToGenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "tbl_movieId")
    val movieId: Int,

    @ColumnInfo(name = "tbl_genreId")
    val genreId: Int
)
