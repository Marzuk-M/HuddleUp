package com.example.huddleup.notifications

import kotlinx.coroutines.delay

object MockNotificationApi {
    suspend fun fetchNotifications(): Map<String, List<Notification>> {
        delay(500) // IDk can change later
        return mapOf(
            "New" to listOf(
                Notification("Test notification. Hello, how are you?", "2025-07-22"),
                Notification("Welcome to HuddleUp!", "2025-07-22")
            ),
            "Yesterday" to listOf(
                Notification("Reminder: Event tomorrow", "2025-07-21"),
                Notification("Update your profile for better matches", "2025-07-21")
            )
        )
    }
}
