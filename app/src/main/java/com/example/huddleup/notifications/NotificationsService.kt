package com.example.huddleup.notifications

import android.util.Log
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

val testNotif = listOf(
    NotificationBlock(
        notificationBlockTitle = "New",
        notifications = listOf(
            ChatNotification(
                teamId = "team123",
                teamName = "CS Study Group",
                message = "Jane: Meeting at 7pm today?",
                timestamp = System.currentTimeMillis() - 1 * 60 * 60 * 1000, // 1 hour ago
            ),
            SystemNotification(
                message = "Your profile was viewed 5 times today!",
                timestamp = System.currentTimeMillis() - 2 * 60 * 60 * 1000, // 2 hours ago
            )
        )
    ),
    NotificationBlock(
        notificationBlockTitle = "Yesterday",
        notifications = listOf(
            ChatNotification(
                teamId = "team456",
                teamName = "Dance Crew",
                message = "Mike: Don't forget practice tomorrow!",
                timestamp = System.currentTimeMillis() - 24 * 60 * 60 * 1000, // 1 day ago
            ),
            SystemNotification(
                message = "Your team 'Dance Crew' has been approved!",
                timestamp = System.currentTimeMillis() - 26 * 60 * 60 * 1000 // 26 hours ago
            )
        )
    )
)

class NotificationsService(private val client: HttpClient) {

    suspend fun loadNotifications(): List<NotificationBlock> {
        Log.d("Service", "loadNotifications")
        delay(500)
        return testNotif
    }
}
