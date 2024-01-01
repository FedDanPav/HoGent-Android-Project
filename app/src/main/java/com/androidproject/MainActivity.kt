package com.androidproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.androidproject.presentation.navigation.AppNavigation

/**
 * The main activity of the app.
 */
class MainActivity : ComponentActivity() {
    /**
     * The onCreate method of the activity.
     * @param savedInstanceState the saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                AppNavigation()
            }
        }
    }
}
