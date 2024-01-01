package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie

/**
 * A movie card that is displayed by various screens
 * Currently required in SearchResultScreen and SavedMoviesScreen
 * @param movie the movie that shuold be displayed
 * @param genres a list of genres
 * @param buttonMethod a method that will either add or delete the movie from the local database
 */
@Composable
fun MovieCard(
    movie: Movie,
    genres: List<Genre>,
    buttonMethod: (movie: Movie) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.47F)
    ) {
        val genresInMovie = genres.filter {
            movie.genreIds!!.contains(it.id)
        }.joinToString { it.name }

        if (genresInMovie.isNotBlank()) {
            Column {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold
                )

                Text(text = stringResource(R.string.description))
                Text(text = movie.overview)

                Text(text = stringResource(R.string.amtvotesAndVoted))
                Text(text = "${movie.voteCount}/${movie.voteAverage}")

                Text(text = "${stringResource(R.string.genres)} $genresInMovie")
            }

            var isSaved by remember { mutableStateOf(movie.isSaved) }

            OutlinedButton(
                onClick = {
                    buttonMethod(movie)
                    movie.isSaved = !movie.isSaved
                    isSaved = !isSaved
                }
            ) {
                if (isSaved) {
                    Icon(
                        painter = painterResource(R.drawable.heart_minus_24px),
                        contentDescription = stringResource(R.string.unsave_button)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.heart_plus_24px),
                        contentDescription = stringResource(R.string.save_button)
                    )
                }
            }
        }
    }
}
