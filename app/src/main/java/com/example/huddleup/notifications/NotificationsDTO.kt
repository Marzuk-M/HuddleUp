package com.example.huddleup.notifications

data class NotificationBlock(
    val notificationBlockTitle: String,
    val notifications: List<NotificationType>
)

sealed interface NotificationType

enum class NotificationTypes {
    SYSTEM,
    CHAT,
}

data class ChatNotification (
    val teamId: String = "",
    val teamName: String = "",
    val message: String = "",
    val timestamp: Long = 0L,
    val type: NotificationTypes = NotificationTypes.CHAT
): NotificationType

data class SystemNotification (
    val message: String = "",
    val timestamp: Long = 0L,
    val type: NotificationTypes = NotificationTypes.SYSTEM
): NotificationType