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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import coil3.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.studyspotz.view.StudySpotViewModel

// Screen for showing the description of a study spot
class SpotDescriptionScreen(private val spot: StudySpot, private val studySpotViewModel: StudySpotViewModel) : Screen {
    @Composable
    override fun Content() {
        SpotDescription(spot, studySpotViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDescription(spot: StudySpot,   studySpotViewModel: StudySpotViewModel) {
    val context = LocalContext.current
    val isFavorited by studySpotViewModel.isFavorite(spot.id).collectAsState(false)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Study Spot",
                        fontSize = 27.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Row {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spot.location))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .height(60.dp)
                            .fillMaxWidth(0.7f)
                    ) {
                        Text(text = "Directions", fontSize = 27.sp)
                    }
                    FloatingActionButton(
                        onClick = { /*do something*/ },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(16.dp)
                            .height(60.dp)
                            .width(60.dp)
                            .fillMaxWidth(0.7f)
                    ) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                "${spot.building} ${spot.room}",
                fontSize = 38.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            HorizontalDivider(thickness = 2.dp)
            Spacer(modifier = Modifier.size(30.dp))

            Text(" Faculty: ${spot.faculty}")

            Spacer(modifier = Modifier.size(20.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(" Amenities:")
                Text(" -   Chargers")
                Text(" -   White/Chalk Board")

            }
            Spacer(modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.weight(1f))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                LazyRow(
                    modifier = Modifier
                        .height(250.dp)
                ) {
                    items(spot.images) { image ->
                        AsyncImage(
                            model = image,
                            contentDescription = "Image of ${spot.building}",
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(40.dp))

            // TODO: Update with favourite UI button
            Text(text = "IS favourite: $isFavorited")
            Button(
                onClick = {
                    studySpotViewModel.toggleFavorite(spot.id)
                    Log.d("SpotDescriptionScreen", "Toggled favorite for ${spot.id}")
                },
            ) {
                Text(text = if (isFavorited) "Remove from Favorites" else "Add to Favorites")
            }
            Text(" Rating")
            AsyncImage(
                model = "https://thumbs.dreamstime.com/b/print-161109189.jpg",
                contentDescription = "Image",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(10.dp))

        }
    }
}
