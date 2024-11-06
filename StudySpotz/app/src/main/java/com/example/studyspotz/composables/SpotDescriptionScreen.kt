package com.example.studyspotz.composables

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.studyspotz.model.StudySpot
import androidx.compose.ui.platform.LocalContext
import android.net.Uri

// Screen for showing the description of a study spot
class SpotDescriptionScreen(private val spot: StudySpot) : Screen {
    @Composable
    override fun Content() {
        SpotDescription(spot)
    }
}

@Composable
fun SpotDescription(spot: StudySpot) {
    Column {
        val context = LocalContext.current
        Text(text = "Name: ${spot.name}")
        Text(text = "Description: ${spot.description}")

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spot.link))
                context.startActivity(intent)
            },
        ) {
            Text(text = "Directions")
        }
    }
}
