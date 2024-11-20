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
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.view.StudySpotViewModel

// Screen for showing the description of a study spot
class SpotDescriptionScreen(
    private val spot: StudySpot,
    private val studySpotViewModel: StudySpotViewModel
) : Screen {
    @Composable
    override fun Content() {
        SpotDescription(spot, studySpotViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotDescription(spot: StudySpot, studySpotViewModel: StudySpotViewModel) {
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    val favoriteSpots by studySpotViewModel.favoriteSpots.collectAsState()
    val isFavorited = favoriteSpots.contains(spot.id)
    val _containerColor = if (isFavorited) Color.Magenta else MaterialTheme.colorScheme.primary

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
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button",
                            tint = MaterialTheme.colorScheme.primary
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
                        onClick = {
                            studySpotViewModel.toggleFavorite(spot.id)
                            Log.d("SpotDescriptionScreen", "Toggled favorite for ${spot.id}")
                        },

                        containerColor = _containerColor,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(16.dp)
                            .height(60.dp)
                            .width(60.dp)
                            .fillMaxWidth(0.7f)
                    )

                    {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorite Button")
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(innerPadding),

            ) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                "${spot.building}",
                fontSize = 42.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                lineHeight = 50.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = spot.room,
                fontSize = 26.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.size(30.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                LazyRow(
                    modifier = Modifier
                        .height(280.dp)
                ) {
                    items(spot.images) { image ->
                        AsyncImage(
                            model = image,
                            contentDescription = "Image of ${spot.building}",
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(30.dp))

            Row(){
                Text("Faculty: ", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
                Text("${spot.faculty}", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text("Amenities:", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.size(5.dp))
            AmenityListItem("Chargers")
            AmenityListItem("Washrooms")
            AmenityListItem("Group Study")

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {


            }
            Spacer(modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.size(40.dp))

        }
    }
}

@Composable
fun AmenityListItem(amenity: String) {
    Row () {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                tint = Color.White,
                contentDescription = "Amenity Icon"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = amenity, fontSize = 28.sp)
    }
    Spacer(modifier = Modifier.size(10.dp))

}
