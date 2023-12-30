package com.androidproject

import com.androidproject.data.remote.dto.genres.GenreDto
import com.androidproject.data.remote.dto.genres.GenreRequestDto
import com.androidproject.data.remote.dto.genres.toDomainObject
import com.androidproject.data.remote.dto.movies.MovieDto
import com.androidproject.data.remote.dto.movies.MovieRequestDto
import com.androidproject.data.remote.dto.movies.toDomainObject
import com.androidproject.model.Movie
import com.androidproject.model.toGenreEntity
import com.androidproject.model.toMovieEntity
import com.androidproject.model.toMovieToGenreEntity

object TestData {
    val testGenreDto = GenreDto(
        100,
        "TestGenre"
    )

    val testGenreRequestDto = GenreRequestDto(
        listOf(testGenreDto)
    )

    val testGenre = testGenreDto.toDomainObject()
    val testGenreEntity = testGenre.toGenreEntity()

    val testMovieDto = MovieDto(
        1,
        "Test Movie",
        "This is a test movie",
        "en-US",
        6.9f,
        96,
        listOf(100)
    )

    val testMovieRequestDto = MovieRequestDto(
        1,
        listOf(testMovieDto),
        1,
        1
    )

    val testMovieSaved = Movie(
        1,
        "Test Movie",
        "This is a test movie",
        "en-US",
        6.9f,
        96,
        listOf(100),
        true
    )

    val testMovie = testMovieDto.toDomainObject()
    val testMovieEntity = testMovie.toMovieEntity()
    val testMovieToGenreEntities = testMovie.toMovieToGenreEntity()

    val testMovieGetArgsString = "include_video=false"
    val testMovieArgsMap = testMovieGetArgsString.split("&").associate {
        val (key, value) = it.split("=")
        key to value
    }
}
