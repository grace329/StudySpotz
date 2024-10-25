package com.example.studyspotz.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.model.StudySpot


// Define the ListScreen
class ListScreen : Screen {
    @Composable
    override fun Content() {
        ListContent()
    }
}

@Composable
fun ListContent() {
    val navigator = LocalNavigator.currentOrThrow

    // Sample list of study spots
    val spots = listOf(
        StudySpot("E2 1003", "Comfy spot with outlets."),
        StudySpot("MC 2013", "For math students."),
        StudySpot("ENV3 1303", "Has 10+ seats."),
        StudySpot("E2 1003", "Comfy spot with outlets."),
        StudySpot("MC 2013", "For math students."),
        StudySpot("ENV3 1303", "Has 10+ seats."),
        StudySpot("E2 1003", "Comfy spot with outlets."),
        StudySpot("MC 2013", "For math students."),
        StudySpot("ENV3 1303", "Has 10+ seats.")
    )

    LazyColumn {
        items(spots) { spot ->
            StudySpotListItem(spot) {
                navigator.push(SpotDescriptionScreen(it))
            }
        }
    }
}
