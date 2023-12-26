package com.androidproject.model

data class Movie(
    val title : String,
    val overview : String,
    val posterPath : String,
    val originalLanguage : String,
    val voteAverage : Float,
    val voteCount : Int
)
