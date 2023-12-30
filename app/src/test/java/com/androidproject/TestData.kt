package com.androidproject

import com.androidproject.data.remote.dto.genres.GenreDto
import com.androidproject.data.remote.dto.genres.GenreRequestDto
import com.androidproject.data.remote.dto.genres.toDomainObject
import com.androidproject.model.toGenreEntity

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


}
