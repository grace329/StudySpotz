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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


// Screen for showing the description of a study spot
class SpotDescriptionScreen(private val spot: StudySpot) : Screen {
    @Composable
    override fun Content() {
        SpotDescription(spot)
    }
}

@Composable
fun SpotDescription(spot: StudySpot) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val context = LocalContext.current

        Text(
            "${spot.building} ${spot.room}",
            fontSize = 40.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text("Description")
        Text("Faculty: ${spot.faculty}")
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(spot.location))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFf17e7e),
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
                containerColor = Color(0xFFf17e7e),
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

        Spacer(modifier = Modifier.size(20.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFfbd8d8)
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(" Amenities:")
            Text(" -   Chargers")
            Text(" -   White/Chalk Board")

        }
        Spacer(modifier = Modifier.size(20.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFfbd8d8)
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
    }
}
