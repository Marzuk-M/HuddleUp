package com.example.huddleup.chat

import android.util.Log
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

val sampleTeam = ChatGroup(
    teamId = "208989asfa",
    teamName = "Team A",
    members = emptyList(),
    messages = emptyList(),
    lastUpdated = 0L
)

class ChatService(private val client: HttpClient) {

    suspend fun loadMetadata(teamId: String): ChatGroup {
        Log.d("Service", "loadNotifications")
        delay(500)
        return sampleTeam
    }
}