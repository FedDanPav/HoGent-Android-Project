package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.R

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, error: String?) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = R.drawable.priority_high_24px),
            contentDescription = stringResource(R.string.error)
        )
        Text(
            text = error ?: stringResource(id = R.string.error),
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * The preview of [ErrorScreen].
 */
@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(modifier = Modifier, error = "Error")
}
