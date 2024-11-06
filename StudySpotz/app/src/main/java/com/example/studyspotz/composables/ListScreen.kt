package com.example.studyspotz.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.AuthState
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.view.StudySpotViewModel

//// Define the ListScreen
//class ListScreen(private val modifier: Modifier, private val authViewModel: AuthViewModel, private val studySpotViewModel: StudySpotViewModel) : Screen {
//    @Composable
//    override fun Content() {
//        ListContent(modifier, authViewModel, studySpotViewModel)
//    }
//}

@Composable
fun ListContent(modifier: Modifier, authViewModel: AuthViewModel, studySpotViewModel: StudySpotViewModel ) {
    val navigator = LocalNavigator.currentOrThrow
    val authState = authViewModel.authState.observeAsState()
    val studySpots by studySpotViewModel.studySpots.collectAsState() // Collect study spots from the ViewModel

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navigator.push(LoginScreen(Modifier, authViewModel,studySpotViewModel ))
            else -> Unit
        }
    }

    LazyColumn {
        items(studySpots) { spot ->
            StudySpotListItem(spot) {
                navigator.push(SpotDescriptionScreen(it)) // Pass the study spot to the next screen
            }
        }

    }
    // Use a Column to stack items vertically
    Column {
        // Sign out button placed above the LazyColumn
        Button(onClick = {
            authViewModel.signout()
        }) {
            Text("Sign Out")
        }
    }

}
