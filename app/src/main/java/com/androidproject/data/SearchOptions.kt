package com.androidproject.data

/**
 * A list of search options which require a boolean as answer
 */
val movieSearchOptionsBools: List<String> = listOf(
    "include_adult"
)

/**
 * A list of search options which require a string user input
 */
val movieSearchOptionsStrings: List<String> = listOf(
    "language", "year", "with_origin_country"
)
