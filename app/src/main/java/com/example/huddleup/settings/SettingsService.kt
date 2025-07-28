package com.example.huddleup.settings

import android.util.Log
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

val sampleUser = UserProfile(
    name = "Alex Jones",
    email = "alex.jones@gmail.com",
    memberSince = "Jan 2023",
    username = "alex_jones",
    notificationEnabled = true
)

class SettingsService(private val client: HttpClient) {

    suspend fun getUserProfile(): UserProfile {
        Log.d("Service", "getUserProfile")
        delay(500)
        return sampleUser
    }

    suspend fun updateUserName(newName: String): Boolean {
        Log.d("Service", "updateUserName")
        delay(500)
        return true
    }

    suspend fun updateNotification(enabled: Boolean): Boolean {
        Log.d("Service", "updateNotification")
        delay(500)
        return true
    }

}
