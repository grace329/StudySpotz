package com.example.studyspotz.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.AuthState
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.model.StudySpot


// Define the ListScreen
class ListScreen(private val modifier: Modifier, private val authViewModel: AuthViewModel) : Screen {
    @Composable
    override fun Content() {
        ListContent(modifier, authViewModel)
    }
}

@Composable
fun ListContent(modifier: Modifier, authViewModel: AuthViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navigator.push(LoginScreen(Modifier, authViewModel))
            else -> Unit
        }
    }

    // Sample list of study spots
    val spots = listOf(
        StudySpot("E2 1003", "Comfy spot with outlets.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("MC 2013", "For math students.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("ENV3 1303", "Has 10+ seats.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("E2 1003", "Comfy spot with outlets.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("MC 2013", "For math students.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("ENV3 1303", "Has 10+ seats.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("E2 1003", "Comfy spot with outlets.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("MC 2013", "For math students.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8"),
        StudySpot("ENV3 1303", "Has 10+ seats.", "https://maps.app.goo.gl/YMgM31zrAaXsnKyU8")
    )

    LazyColumn {
        items(spots) { spot ->
            StudySpotListItem(spot) {
                navigator.push(SpotDescriptionScreen(it))
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
