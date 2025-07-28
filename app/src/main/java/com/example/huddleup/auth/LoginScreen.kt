package com.example.huddleup.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.Routes
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.example.huddleup.sharedcomponents.HUTextButton
import com.example.huddleup.sharedcomponents.HUTextField
import com.example.huddleup.sharedcomponents.HUTextFieldSpacer
import com.example.huddleup.sharedcomponents.PageHeader
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Scaffold (topBar = { PageHeader(title = "HuddleUp") }) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ){
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
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            HUTextFieldSpacer()

            // TODO: MAYBE REPLACE THIS WITH SHARED COMPONENT THIS LATER
            OutlinedButton (
                onClick = {
                    AuthService.login(
                        email.value,
                        password.value,
                        onSuccess = {
                            navController.navigate(Routes.DASHBOARD) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        },
                        onFailure = {}
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Login", color = MaterialTheme.colorScheme.primary)
            }

            HUTextFieldSpacer(modifier = Modifier.weight(1f))

            HUDividerWithText(text = "Don't have an account?")
            HUTextButton(
                label = "Sign Up!",
                onClick = { navController.navigate(Routes.SIGNUP) }
            )
        }
    }
}