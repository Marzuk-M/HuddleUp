package com.example.huddleup.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huddleup.Routes

@Composable
fun SignUpScreen(
    navController: NavController
) {
    Scaffold (
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "HuddleUp", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ){
            // TODO: ADD FIELDS SIMILAR TO THE LOGIN SCREEN
            // TODO: ADD FIELD FOR FIRST NAME
            // TODO: ADD FIELD FOR LAST NAME
            // TODO: ADD FIELD FOR PASSWORD
            // TODO: ADD FIELD FOR CONFIRM PASSWORD
            // TODO: ADD FIELD FOR USERNAME
            // TODO: ADD FIELD FOR EMAIL
            // TODO: ADD FIELD FOR DATE OF BIRTH

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: MAYBE CHANGE THIS LATER
            OutlinedButton (
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Sign Up!", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.fillMaxSize().weight(1f))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = Dp(1f)
                )

                Text(text = "Already have an account?", modifier = Modifier.padding(8.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = Dp(1f)
                )
            }

            TextButton(
                onClick = { navController.navigate(Routes.LOGIN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}