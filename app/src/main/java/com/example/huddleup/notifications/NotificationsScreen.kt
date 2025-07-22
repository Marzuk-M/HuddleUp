package com.example.huddleup.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.PageHeader

@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = viewModel()
) {
    val notificationsMap by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = { PageHeader(title = "Notifications") }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxSize()
                .background(Color(0xFFF6ECEC))
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (notificationsMap.isEmpty()) {
                Text("Loading...", color = Color.Gray)
            } else {
                notificationsMap.forEach { (section, notifications) ->
                    SectionHeader(title = section)
                    notifications.forEach { notification ->
                        NotificationItem(notification.message)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
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
