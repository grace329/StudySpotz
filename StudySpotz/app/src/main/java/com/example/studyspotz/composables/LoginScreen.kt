package com.example.studyspotz.composables
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// Define LoginScreen
class LoginScreen : Screen {
    @Composable
    override fun Content(){
        LoginContent()
    }
}

@Composable
fun LoginContent() {
    val navigator = LocalNavigator.currentOrThrow

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navigator.push(ListScreen()) // Navigate to ListScreen
        }) {
            Text("Login")
        }
    }
}

/*
// turn this into one composable for user authentication. pass in text as Login or Register

@Composable
fun LoginContent(email: String,
                 onEmailChange: (String) -> Unit,
                 password: String,
                 onPasswordChange: (String) -> Unit,
                 onButtonClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Login")
        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = onPasswordChange, label = { Text("Password") })
        Button(onClick = onButtonClick) { Text("Login") }
        Text("Click to Register")
    }
}*/