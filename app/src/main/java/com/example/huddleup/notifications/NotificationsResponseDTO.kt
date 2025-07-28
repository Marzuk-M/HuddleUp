package com.example.huddleup.notifications

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsResponse(
    val success: Boolean,
    val data: List<NotificationBlockResponse>? = null,
    val message: String? = null
)

@Serializable
data class NotificationBlockResponse(
    val notificationBlockTitle: String,
    val notifications: List<NotificationResponse>
)

@Serializable
data class NotificationResponse(
    val teamId: String? = null,
    val teamName: String? = null,
    val message: String,
    val timestamp: Long,
    val type: String
) 