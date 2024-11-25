package com.example.studyspotz.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.TypedValueCompat.dpToPx
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.R
import com.example.studyspotz.view.StudySpotViewModel


// Define the HomeScreen
class HomeScreen(private val modifier: Modifier, private val authViewModel: AuthViewModel, private val studySpotViewModel: StudySpotViewModel) :
    Screen {
    @Composable
    override fun Content() {
        HomeContent(modifier, authViewModel, studySpotViewModel)
    }
}

@Composable
fun FilterWithDropdown(onFilterSelected: (String) -> Unit) {
    var menuOpen by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("All") }

    Box () {
        // Filter button
        Button(onClick = { menuOpen = !menuOpen }) {
            Row {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(18.dp)
                )
                Text("$selectedItem", Modifier.padding(start = 8.dp))
            }
        }

        // Filter menu
        DropdownMenu(
            expanded = menuOpen,
            onDismissRequest = { menuOpen = false },
            offset = DpOffset(x = 0.dp, y = 10.dp)
        ) {
            listOf("All", "Math", "Arts", "Science", "Engineering", "Environment", "Health").forEach { faculty ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = faculty
                        menuOpen = false
                        onFilterSelected(faculty)
                    },
                    text = { Text(faculty) }
                )
            }
        }
    }
}

private @Composable
fun HomeContent(modifier: Modifier, authViewModel: AuthViewModel, studySpotViewModel: StudySpotViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val authState = authViewModel.authState.observeAsState()
    val studySpots by studySpotViewModel.studySpots.collectAsState() // Collect study spots from the ViewModel
    var search by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var isListView by remember { mutableStateOf(true)}
    var profileClicked by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)){
            // Search
            OutlinedTextField(
                value = search,
                onValueChange = {  search = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Search for spots...", fontSize = 18.sp) },
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )                },
                singleLine = true
            )
            // Profile with Sign Out
            Box () {
                IconButton(
                    onClick = { profileClicked = !profileClicked },
                    modifier = Modifier
                        .aspectRatio(1f)
                ) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Account",
                        modifier = Modifier.size(36.dp)
                    )
                }
                DropdownMenu(
                    expanded = profileClicked,
                    onDismissRequest = { profileClicked = false },
                    offset = DpOffset(x = 0.dp, y = 10.dp)
                ) {
                    DropdownMenuItem(
                        onClick = { authViewModel.signout() },
                        text = { Text("Sign Out") }
                    )
                }
            }
        }
        Row (modifier = Modifier.fillMaxWidth()) {
            FilterWithDropdown { selectedFilter ->
                filter = selectedFilter
            }
            Spacer(modifier = Modifier.weight(1f))

            IconToggleButton(
                checked = isListView,
                onCheckedChange = { isListView = !isListView },
                modifier = Modifier
                    .background(
                        if (!isListView) Color.White else MaterialTheme.colorScheme.primary // Background color toggle
                    )
                    .size(48.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.list_view),
                    contentDescription = if (isListView) "Switch to Gallery View" else "Switch to List View",
                    tint = if (!isListView) MaterialTheme.colorScheme.primary else Color.White
                )
            }
            // grid view
            IconToggleButton(
                checked = !isListView,
                onCheckedChange = { isListView = !isListView },
                modifier = Modifier
                    .background(
                        if (isListView) Color.White else MaterialTheme.colorScheme.primary
                    )
                    .size(48.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.grid_view),
                    contentDescription = if (isListView) "Switch to Gallery View" else "Switch to List View",
                    tint = if (isListView) MaterialTheme.colorScheme.primary else Color.White
                )
            }
            }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            if (isListView) {
                ListContent(Modifier, authViewModel, studySpotViewModel, search, filter) // Nested composable

            } else {
                GalleryContent(Modifier, authViewModel, studySpotViewModel, search, filter)
            }
            }
    }
}
