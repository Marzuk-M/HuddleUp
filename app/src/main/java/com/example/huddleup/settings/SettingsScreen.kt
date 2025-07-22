package com.example.huddleup.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    val backgroundColor = Color(0xFFF9F1F0)
    val textColor = Color(0xFF5C4033) // Soft brown
    val borderColor = Color(0xFF5C4033)
    val buttonColor = Color(0xFFD4A8A3)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Text(
                    text = "Settings",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Settings Items
                SettingsItem("Profile")
                SettingsItem("Notifications")
                SettingsItem("Privacy")
            }

            // Logout Button
            OutlinedButton(
                onClick = {
                    // TODO: Call AuthService.logout() and navigate
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = buttonColor,
                    contentColor = textColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SettingsItem(label: String) {
    val borderColor = Color(0xFF5C4033)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 0.dp)
            .clickable { /* Handle navigation */ }
            .border(1.dp, borderColor, shape = RoundedCornerShape(0.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 30.dp)
                .weight(1f),
            color = borderColor,
            fontSize = 24.sp
        )
        Text(
            text = "›",
            modifier = Modifier.padding(end = 12.dp),
            color = borderColor,
            fontSize = 24.sp
        )
    }
}
