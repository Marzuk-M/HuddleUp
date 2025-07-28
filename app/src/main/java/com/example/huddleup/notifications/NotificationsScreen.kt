package com.example.huddleup.notifications

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huddleup.sharedcomponents.HUDividerWithText
import com.example.huddleup.sharedcomponents.PageHeader

@Composable
fun NotificationsScreen (
    navController: NavController,
    viewModel: NotificationsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    val notificationsData by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold (
        topBar = { PageHeader(title = "Notifications") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.loadNotifications() },
                modifier = Modifier.padding(bottom = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh notifications"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Error loading notifications",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error ?: "Unknown error",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                notificationsData.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No notifications ;(", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            notificationsData.forEach { block ->
                                HUDividerWithText(block.notificationBlockTitle)

                                block.notifications.forEach { notification ->
                                    when (notification) {
                                        is SystemNotification -> {
                                            SystemNotificationCard(notification)
                                        }
                                        is ChatNotification -> {
                                            ChatNotificationCard(navController, notification)
                                        }
                                    }
                                    Spacer(modifier = Modifier.padding(bottom = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SystemNotificationCard(
    notification: SystemNotification
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = notification.message,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ChatNotificationCard(
    navController: NavController,
    notification: ChatNotification
) {
    val messageBody = notification.message
        .let { if (it.length > 35) it.take(35) + "..." else it }

    Card(
        onClick = { navController.navigate("chat/${notification.teamId}") },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Message from @${notification.teamName}",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = messageBody,
            )
        }
    }
}