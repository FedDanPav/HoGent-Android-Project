package com.androidproject

import com.androidproject.data.local.entity.GenreEntity
import com.androidproject.data.local.entity.MovieEntity
import com.androidproject.data.local.entity.MovieToGenreEntity
import com.androidproject.data.local.entity.toGenre

object TestData {
    val testMovieToGenreEntity = MovieToGenreEntity(
        movieId = 1,
        genreId = 1
    )

    val testMovieEntity = MovieEntity(
        1,
        "Test Movie",
        "This is a test movie",
        "en-US",
        6.9f,
        96
    )

    val testGenreEntity = GenreEntity(
        1,
        "TestGenre"
    )

    val testGenre = testGenreEntity.toGenre()
}
