package com.example.huddleup.notifications

import android.util.Log
import com.example.huddleup.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.delay

class NotificationsService(private val client: HttpClient) {

    suspend fun loadNotifications(authToken: String? = null): List<NotificationBlock> {
        Log.d("Service", "loadNotifications")
        
        return try {
            val response = client.get(Endpoints.getNotificationsEndpoint()) {
                contentType(ContentType.Application.Json)
                authToken?.let { token ->
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
            }
            
            val notificationsResponse: NotificationsResponse = response.body()
            
            if (notificationsResponse.success && notificationsResponse.data != null) {
                // Convert API response to domain models
                notificationsResponse.data.map { blockResponse ->
                    NotificationBlock(
                        notificationBlockTitle = blockResponse.notificationBlockTitle,
                        notifications = blockResponse.notifications.map { notificationResponse ->
                            when (notificationResponse.type) {
                                "CHAT" -> ChatNotification(
                                    teamId = notificationResponse.teamId ?: "",
                                    teamName = notificationResponse.teamName ?: "",
                                    message = notificationResponse.message,
                                    timestamp = notificationResponse.timestamp
                                )
                                "SYSTEM" -> SystemNotification(
                                    message = notificationResponse.message,
                                    timestamp = notificationResponse.timestamp
                                )
                                else -> SystemNotification(
                                    message = notificationResponse.message,
                                    timestamp = notificationResponse.timestamp
                                )
                            }
                        }
                    )
                }
            } else {
                Log.e("Service", "Failed to load notifications: ${notificationsResponse.message}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Service", "Error loading notifications", e)
            // Fallback to empty list on error
            emptyList()
        }
    }
}
