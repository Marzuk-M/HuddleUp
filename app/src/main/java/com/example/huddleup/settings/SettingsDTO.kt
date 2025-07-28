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