package com.androidproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidproject.model.Movie

/**
 * A database entity for the movies
 * @param id a movieId used by the TMDB to find and identify the movie
 * @param title title of the movie
 * @param overview a description of the movie
 * @param originalLanguage the original language of the movie
 * @param voteAverage the average vote
 * @param voteCount the amount of people who shared a vote on the movie
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movieId") val id: Int,
    val title : String,
    val overview : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int
)

/**
 * Converts the [MovieEntity] into a [Movie]
 * @return the converted [Movie]
 */
fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        genreIds = null,
        isSaved = true
    )
}
