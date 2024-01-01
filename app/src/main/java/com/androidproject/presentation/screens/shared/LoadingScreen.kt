package com.androidproject.presentation.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.R

/**
 * The screen that apprears when data is loading
 * @param modifier the modifier to apply
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The preview of [LoadingScreen].
 */
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}
