package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.androidproject.R
import com.androidproject.model.Genre
import com.androidproject.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    genres: List<Genre>,
    saved: Boolean = false,
    buttonMethod: (movie: Movie) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.47F)
    ) {
        val genresInMovie =  genres.filter {
            movie.genreIds!!.contains(it.id)
        }.map { it.name }.joinToString()

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

            if (saved) {
                OutlinedButton(
                    onClick = { buttonMethod(movie) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart_minus_24px),
                        contentDescription = stringResource(R.string.unsave_button)
                    )
                }
            } else {
                OutlinedButton(
                    onClick = { buttonMethod(movie) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.heart_plus_24px),
                        contentDescription = stringResource(R.string.save_button)
                    )
                }
            }
        }
    }
}
