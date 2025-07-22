package com.example.huddleup.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
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
fun NotificationsScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6ECEC))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notifications 🔔",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // New Section
        SectionHeader(title = "New")
        NotificationItem("Test notification. hello how are you?")
        NotificationItem("Test notification. hello")

        Spacer(modifier = Modifier.height(16.dp))

        // Yesterday Section
        SectionHeader(title = "Yesterday")
        NotificationItem("Test notification. hello")
        NotificationItem("Test notification. hello")
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f), color = Color.DarkGray, thickness = 1.dp)
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Black
        )
        Divider(modifier = Modifier.weight(1f), color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
fun NotificationItem(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFDCCCCC), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = message, color = Color.Black)
    }
}
