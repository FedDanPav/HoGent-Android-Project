package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie

/**
 * A result overview that is run by different screens
 * Currently required in SearchResultScreen and SavedMoviesScreen
 * @param genres a list of genres
 * @param movies a list of movies which should be displayed
 * @param handleMovie a method that will either add or delete the movie from the local database
 */
@Composable
fun ResultOverview(
    genres : List<Genre>,
    movies : List<Movie>,
    handleMovie: (movie: Movie) -> Unit
) {
    if (movies.isEmpty()) {
        Text(text = stringResource(R.string.no_connection))
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 88.dp, start = 12.dp, end = 12.dp)
        ) {
            items(movies) {
                MovieCard(movie = it, genres = genres, buttonMethod = handleMovie)
            }
        }
    }
}
