package com.example.studyspotz.composables

import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.view.StudySpotViewModel
import com.example.studyspotz.model.StudySpot
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.R
import com.example.studyspotz.composables.SpotDescriptionScreen
import coil3.compose.AsyncImage


class GalleryScreen(
    private val modifier: Modifier,
    private val authViewModel: AuthViewModel,
    private val studySpotViewModel: StudySpotViewModel
) : Screen {
    @Composable
    override fun Content() {
        GalleryContent(modifier, authViewModel, studySpotViewModel)
    }
}

@Composable
fun GalleryContent(modifier: Modifier, authViewModel: AuthViewModel, studySpotViewModel: StudySpotViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val studySpots by studySpotViewModel.studySpots.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Adjust columns?
            modifier = Modifier.fillMaxSize()
        ) {
            items(studySpots) { spot ->
                StudySpotCard(spot) {
                    navigator.push(SpotDescriptionScreen(spot, studySpotViewModel))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { authViewModel.signout() }) {
            Text("Sign Out", fontSize = 20.sp)
        }
    }
}

@Composable
fun StudySpotCard(spot: StudySpot, onClick: (StudySpot) -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 180.dp, height = 250.dp)
            .padding(10.dp)
            .clickable { onClick(spot) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = spot.images.getOrNull(0),
                contentDescription = "Main Image of ${spot.building}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = spot.building, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = spot.room, fontSize = 14.sp, color = Color.Gray)
        }
    }
}



