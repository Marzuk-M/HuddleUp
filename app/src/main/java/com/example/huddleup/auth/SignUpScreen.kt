package com.example.huddleup.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.Routes
import com.example.huddleup.sharedcomponents.PageHeader
import com.example.huddleup.ui.theme.CocoaBrown
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.example.huddleup.sharedcomponents.HUTextButton
import com.example.huddleup.sharedcomponents.HUTextField
import com.example.huddleup.sharedcomponents.HUTextFieldSpacer

@Composable
fun SignUpScreen(
    navController: NavController
) {
    var firstName = remember { mutableStateOf("") }
    var lastName = remember { mutableStateOf("") }
    var dob = remember { mutableStateOf("") }
    var username = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirmPassword = remember { mutableStateOf("") }

    Scaffold(topBar = { PageHeader(title = "HuddleUp") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            HUTextFieldSpacer()
            HUTextField(
                value = firstName,
                label = "First Name"
            )

            HUTextFieldSpacer()
            HUTextField(
                value = lastName,
                label = "Last Name"
            )

            HUTextFieldSpacer()
            HUTextField(
                value = dob,
                label = "Date of Birth",
                keyboardType = KeyboardType.Text // TODO: Fix this to add date picker
            )

            HUDividerWithText(text = "Authentication")

            HUTextField(
                value = username,
                label = "Choose a unique username",
            )

            HUTextFieldSpacer()
            HUTextField(
                value = email,
                label = "Email",
                keyboardType = KeyboardType.Email
            )

            HUTextFieldSpacer()
            HUTextField(
                value = password,
                label = "Password",
                keyboardType = KeyboardType.Password
            )

            HUTextFieldSpacer()
            HUTextField(
                value = confirmPassword,
                label = "Confirm Password",
                keyboardType = KeyboardType.Password
            )

            HUTextFieldSpacer()

            // TODO: MAYBE REPLACE THIS WITH SHARED COMPONENT THIS LATER
            OutlinedButton(
                onClick = { /* Handle signup */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 3.dp,
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = CocoaBrown.copy(alpha = 0.3f),
                    contentColor = CocoaBrown
                )
            ) {
                Text(text = "Sign up", fontWeight = FontWeight.Bold)
            }

            HUTextFieldSpacer(modifier = Modifier.weight(1f))

            HUDividerWithText(text = "Already have an account?")

            HUTextButton(
                label = "Login",
                onClick = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}
