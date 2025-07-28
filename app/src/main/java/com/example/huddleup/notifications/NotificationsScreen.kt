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
import com.example.huddleup.sharedcomponents.PageHeader

@Composable
fun NotificationsScreen(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(16.dp)
    ) {
        // Header
        PageHeader(title = "Notifications")

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
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Divider(modifier = Modifier.weight(1f), color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
fun NotificationItem(message: String) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(colors.primaryContainer, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(text = message, color = Color.Black)
    }
}
