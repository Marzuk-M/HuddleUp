package com.example.huddleup.settings

data class UserProfile(
    val name: String,
    val email: String,
    val username: String,
    val memberSince: String,
    val notificationEnabled: Boolean
)