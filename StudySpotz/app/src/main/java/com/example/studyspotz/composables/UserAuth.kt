// imports
// define LoginScreen
class LoginScreen : Screen {
    @Composable
    override fun Content(){
        LoginContent();
    }
}

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
}