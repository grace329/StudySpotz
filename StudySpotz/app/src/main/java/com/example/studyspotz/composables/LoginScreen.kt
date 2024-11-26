package com.example.studyspotz.composables
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyspotz.AuthState
import com.example.studyspotz.AuthViewModel
import com.example.studyspotz.R
import com.example.studyspotz.view.StudySpotViewModel
import androidx.compose.foundation.Image


// Define LoginScreen
class LoginScreen(private val modifier: Modifier, private val authViewModel: AuthViewModel, private val studySpotViewModel: StudySpotViewModel) : Screen {
    @Composable
    override fun Content(){
        LoginContent(modifier, authViewModel, studySpotViewModel)
    }
}

@Composable
fun LoginContent(modifier: Modifier, authViewModel: AuthViewModel, studySpotViewModel: StudySpotViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var showPassword by remember { mutableStateOf(false)}

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navigator.push(HomeScreen(Modifier, authViewModel,studySpotViewModel))
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.firstlogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

        Text("Login to an Existing Account")
        TextField(
            value = email,
            onValueChange = {  email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
        )
        TextField(
            value = password,
            onValueChange = {  password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            trailingIcon = {
                val icon = if (showPassword) R.drawable.visible else R.drawable.visibility_off
                Icon (
                    painter = painterResource(id = icon),
                    contentDescription = "show/hide password",
                    modifier = Modifier.clickable { showPassword = !showPassword }
                )
            }
            )
        Button(onClick = {
            authViewModel.login(email, password)
        }) {
            Text(text ="Login", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navigator.push(SignupScreen(modifier, authViewModel, studySpotViewModel)) }) {
                Text(text = "Click to Create Account", fontSize = 20.sp)
        }

    }
}
