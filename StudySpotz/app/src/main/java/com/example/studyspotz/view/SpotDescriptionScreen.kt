package com.example.studyspotz.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.studyspotz.model.StudySpot

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
        Text(text = "Name: ${spot.name}")
        Text(text = "Description: ${spot.description}")
    }
}
