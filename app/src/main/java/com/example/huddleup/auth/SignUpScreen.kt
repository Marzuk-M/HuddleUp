package com.example.huddleup.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.Routes
import com.example.huddleup.ui.theme.CocoaBrown

@Composable
fun SignUpScreen(
    navController: NavController
) {
    val lightPink = Color(0xFFFDEFEF)

    Scaffold(
        containerColor = lightPink,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "HuddleUp",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = CocoaBrown
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightPink)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // State variables
            var firstName by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var dob by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            CustomTextField("First Name", firstName) { firstName = it }
            CustomTextField("Last Name", lastName) { lastName = it }
            CustomTextField("Date of Birth", dob) { dob = it }
            CustomTextField("Username", username) { username = it }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                    //.padding(top = 24.dp, bottom = 24.dp),
                color = CocoaBrown
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField("Email", email, keyboardType = KeyboardType.Email) { email = it }
            CustomTextField("Password", password, keyboardType = KeyboardType.Password, isPassword = true) { password = it }
            CustomTextField("Confirm Password", confirmPassword, keyboardType = KeyboardType.Password, isPassword = true) { confirmPassword = it }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button
            OutlinedButton(
                onClick = { /* Handle signup */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 3.dp,
                    //color = CocoaBrown
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CocoaBrown.copy(alpha = 0.3f),
                    contentColor = CocoaBrown
                )
            ) {
                Text(text = "Sign up", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Divider with text
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = CocoaBrown.copy(alpha = 0.3f))
                Text(
                    text = "Already have an account?",
                    color = CocoaBrown,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Divider(modifier = Modifier.weight(1f), color = CocoaBrown.copy(alpha = 0.3f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Log in Button
            OutlinedButton(
                onClick = { navController.navigate(Routes.LOGIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 3.dp,
                    //color = CocoaBrown
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CocoaBrown.copy(alpha = 0.3f),
                    contentColor = CocoaBrown
                )
            ) {
                Text(text = "Log in", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFADD8E6), // Light blue
            unfocusedIndicatorColor = Color(0xFFADD8E6),
            cursorColor = CocoaBrown
        )
    )
}
