package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.androidproject.model.Genre
import com.androidproject.model.Movie

@Composable
fun MovieCard(movie: Movie, genres: List<Genre>) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.47F)
    ) {
        val genresInMovie =  genres.filter {
            movie.genreIds!!.contains(it.id)
        }.map { it.name }.joinToString()

        if (genresInMovie.isNotBlank()) {
            Row {
                Column {
                    Text(text = movie.title)

                    VerticalDivider()

                    Text(text = "Description")
                    Text(text = movie.overview)

                    VerticalDivider()

                    Text(text = "Votes/average")
                    Text(text = "${movie.voteCount}/${movie.voteAverage}")

                    VerticalDivider()

                    Text(text = "Genres: $genresInMovie")
                }
            }


        }
    }
}
