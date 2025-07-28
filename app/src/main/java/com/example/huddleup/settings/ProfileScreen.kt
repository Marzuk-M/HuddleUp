package com.example.huddleup.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.PageHeader

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart),
            horizontalAlignment = Alignment.Start
        ) {
            PageHeader(title = "Profile")

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Name", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "John Doe", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))

            Text(text = "Email", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "john.doe@example.com", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp))

            Text(text = "Member Since", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "January 2024", fontSize = 16.sp)
        }
    }
}
