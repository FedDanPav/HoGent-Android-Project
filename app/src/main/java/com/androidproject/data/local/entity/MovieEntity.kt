package com.androidproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidproject.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movieId") val id: Int,
    val title : String,
    val overview : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genreIds = null
    )
}
