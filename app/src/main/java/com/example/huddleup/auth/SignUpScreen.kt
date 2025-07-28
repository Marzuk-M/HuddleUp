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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.Routes
import com.example.huddleup.sharedcomponents.PageHeader
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.example.huddleup.sharedcomponents.HUTextButton
import com.example.huddleup.sharedcomponents.HUTextField
import com.example.huddleup.sharedcomponents.HUTextFieldSpacer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var firstName = remember { mutableStateOf("") }
    var lastName = remember { mutableStateOf("") }
    var dob = remember { mutableStateOf("") } // TODO: REPLACE THIS WITH DATE PICKER
    var username = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirmPassword = remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

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
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        AuthService.signup(
                            email = email.value,
                            password = password.value,
                            firstName = firstName.value,
                            lastName = lastName.value,
                            username = username.value,
                            dateOfBirth = dob.value,
                            onSuccess = {
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(Routes.SIGNUP) { inclusive = true }
                                }
                            },
                            onFailure = { },
                            context = context
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 3.dp,
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colors.primaryContainer,
                    contentColor = colors.primary
                )
            ) {
                Text(text = "Sign Up!", fontWeight = FontWeight.Bold)
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
