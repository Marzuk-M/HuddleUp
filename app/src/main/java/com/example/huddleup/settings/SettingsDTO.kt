package com.example.huddleup.settings

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String,
    val email: String,
    val username: String,
    val memberSince: String,
    val notificationEnabled: Boolean
)

@Serializable
data class UpdateNameRequest(
    val name: String
)

@Serializable
data class UpdateNotificationRequest(
    val notificationEnabled: Boolean
)