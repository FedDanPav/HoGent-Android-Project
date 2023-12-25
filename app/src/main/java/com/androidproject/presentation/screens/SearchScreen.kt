package com.androidproject.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.data.movieSearchOptionsBools
import com.androidproject.data.movieSearchOptionsStrings

@Composable
fun SearchScreen(paddingValues: PaddingValues){
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        LazyHorizontalGrid(
            modifier = Modifier
                .height((movieSearchOptionsStrings.size).times(70).dp)
                .fillMaxWidth(),
            rows = GridCells.Fixed(movieSearchOptionsStrings.size),
            horizontalArrangement = Arrangement.Center
        ) {
            items(movieSearchOptionsStrings) { option ->
                Column {
                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    OutlinedTextField(
                        value = "",
                        onValueChange = {}
                    )
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.FixedSize(100.dp)
        ) {
            items(movieSearchOptionsBools) { option ->
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = option)
                }
            }
        }

        HorizontalDivider()

        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen(paddingValues = PaddingValues())
}
