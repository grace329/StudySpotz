package com.example.studyspotz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.view.StudySpotViewModel

// Define the HomeScreen
class HomeScreen(private val modifier: Modifier, private val authViewModel: AuthViewModel, private val studySpotViewModel: StudySpotViewModel) :
    Screen {
    @Composable
    override fun Content() {
        HomeContent(modifier, authViewModel, studySpotViewModel)
    }
}

private @Composable
fun HomeContent(modifier: Modifier, authViewModel: AuthViewModel, studySpotViewModel: StudySpotViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val authState = authViewModel.authState.observeAsState()
    val studySpots by studySpotViewModel.studySpots.collectAsState() // Collect study spots from the ViewModel
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(){
            OutlinedTextField(
                value = search,
                onValueChange = {  search = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Search for spots...") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )                },
                singleLine = true
            )
            IconButton(
                onClick = { authViewModel.signout() },
                modifier = Modifier.size(48.dp)) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "Account"
                )
            }
        }
        Text("this is the home screen")
        Text("hello")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Adjust as needed
                .padding(8.dp)
        ) {
            ListContent(Modifier, authViewModel,studySpotViewModel) // Nested composable
        }
    }
}
