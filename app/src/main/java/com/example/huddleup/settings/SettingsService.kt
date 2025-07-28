package com.example.huddleup.settings

import android.util.Log
import com.example.huddleup.Endpoints
import com.example.huddleup.auth.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class UpdateNameRequest(
    val name: String
)

@Serializable
data class UpdateNotificationRequest(
    val notificationEnabled: Boolean
)

class SettingsService(private val client: HttpClient) {

    suspend fun getUserProfile(): UserProfile {
        Log.d("SettingsService", "Getting user profile")
        
        val token = AuthService.getFirebaseAuthToken()
        if (token == null) {
            throw Exception("User not authenticated")
        }

        return try {
            val response = client.get(Endpoints.getProfileEndpoint()) {
                header("Authorization", "Bearer $token")
            }
            
            val profile = response.body<UserProfile>()
            Log.d("SettingsService", "Profile retrieved successfully: $profile")
            profile
        } catch (e: Exception) {
            Log.e("SettingsService", "Error getting user profile", e)
            throw e
        }
    }

    suspend fun updateUserName(newName: String): Boolean {
        Log.d("SettingsService", "Updating user name to: $newName")
        
        val token = AuthService.getFirebaseAuthToken()
        if (token == null) {
            throw Exception("User not authenticated")
        }

        return try {
            val request = UpdateNameRequest(name = newName)
            
            val response = client.put(Endpoints.updateNameEndpoint()) {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            
            Log.d("SettingsService", "Name updated successfully")
            true
        } catch (e: Exception) {
            Log.e("SettingsService", "Error updating user name", e)
            throw e
        }
    }

    suspend fun updateNotification(enabled: Boolean): Boolean {
        Log.d("SettingsService", "Updating notification setting to: $enabled")
        
        val token = AuthService.getFirebaseAuthToken()
        if (token == null) {
            throw Exception("User not authenticated")
        }

        return try {
            val request = UpdateNotificationRequest(notificationEnabled = enabled)
            
            val response = client.put(Endpoints.updateNotificationEndpoint()) {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            
            Log.d("SettingsService", "Notification setting updated successfully")
            true
        } catch (e: Exception) {
            Log.e("SettingsService", "Error updating notification setting", e)
            throw e
        }
    }
}
