package com.example.huddleup.chat

import android.util.Log
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

val sampleUsers = listOf(
    User(username = "alice", name = "Alice Johnson", profileImageUrl = null),
    User(username = "bob", name = "Bob Smith", profileImageUrl = null),
    User(username = "carol", name = "Carol Lee", profileImageUrl = null)
)

val sampleTeam = ChatGroup(
    teamId = "208989asfa",
    teamName = "Team A",
    members = emptyList(),
    messages = listOf(
        Message(
            id = "m1",
            sender = sampleUsers[0],
            content = "Hey team! Are we still on for the meeting today?",
            timestamp = System.currentTimeMillis() - 3_600_000, // 1 hour ago
            seenBy = listOf("bob", "carol")
        ),
        Message(
            id = "m2",
            sender = sampleUsers[1],
            content = "Yes! I’ll be there in 10 minutes.",
            timestamp = System.currentTimeMillis() - 3_000_000, // 50 min ago
            seenBy = listOf("alice", "carol")
        ),
        Message(
            id = "m3",
            sender = sampleUsers[2],
            content = "Same here. I just need to grab a coffee first",
            timestamp = System.currentTimeMillis() - 2_500_000, // ~42 min ago
            seenBy = listOf("alice", "bob")
        ),
        Message(
            id = "m4",
            sender = sampleUsers[0],
            content = "Awesome. See you both soon!",
            timestamp = System.currentTimeMillis() - 2_000_000, // ~33 min ago
            seenBy = listOf("bob")
        ),
        Message(
            id = "m1",
            sender = sampleUsers[0],
            content = "Hey team! Are we still on for the meeting today?",
            timestamp = System.currentTimeMillis() - 3_600_000, // 1 hour ago
            seenBy = listOf("bob", "carol")
        ),
        Message(
            id = "m2",
            sender = sampleUsers[1],
            content = "Yes! I’ll be there in 10 minutes.",
            timestamp = System.currentTimeMillis() - 3_000_000, // 50 min ago
            seenBy = listOf("alice", "carol")
        ),
        Message(
            id = "m3",
            sender = sampleUsers[2],
            content = "Same here. I just need to grab a coffee first",
            timestamp = System.currentTimeMillis() - 2_500_000, // ~42 min ago
            seenBy = listOf("alice", "bob")
        ),
        Message(
            id = "m4",
            sender = sampleUsers[0],
            content = "Awesome. See you both soon!",
            timestamp = System.currentTimeMillis() - 2_000_000, // ~33 min ago
            seenBy = listOf("bob")
        ),
        Message(
            id = "m1",
            sender = sampleUsers[0],
            content = "Hey team! Are we still on for the meeting today?",
            timestamp = System.currentTimeMillis() - 3_600_000, // 1 hour ago
            seenBy = listOf("bob", "carol")
        ),
        Message(
            id = "m2",
            sender = sampleUsers[1],
            content = "Yes! I’ll be there in 10 minutes.",
            timestamp = System.currentTimeMillis() - 3_000_000, // 50 min ago
            seenBy = listOf("alice", "carol")
        ),
        Message(
            id = "m3",
            sender = sampleUsers[2],
            content = "Same here. I just need to grab a coffee first",
            timestamp = System.currentTimeMillis() - 2_500_000, // ~42 min ago
            seenBy = listOf("alice", "bob")
        ),
        Message(
            id = "m4",
            sender = sampleUsers[0],
            content = "Awesome. See you both soon!",
            timestamp = System.currentTimeMillis() - 2_000_000, // ~33 min ago
            seenBy = listOf("bob")
        ),
        Message(
            id = "m1",
            sender = sampleUsers[0],
            content = "Hey team! Are we still on for the meeting today?",
            timestamp = System.currentTimeMillis() - 3_600_000, // 1 hour ago
            seenBy = listOf("bob", "carol")
        ),
        Message(
            id = "m2",
            sender = sampleUsers[1],
            content = "Yes! I’ll be there in 10 minutes.",
            timestamp = System.currentTimeMillis() - 3_000_000, // 50 min ago
            seenBy = listOf("alice", "carol")
        ),
        Message(
            id = "m3",
            sender = sampleUsers[2],
            content = "Same here. I just need to grab a coffee first",
            timestamp = System.currentTimeMillis() - 2_500_000, // ~42 min ago
            seenBy = listOf("alice", "bob")
        ),
        Message(
            id = "m4",
            sender = sampleUsers[0],
            content = "Awesome. See you both soon!",
            timestamp = System.currentTimeMillis() - 2_000_000, // ~33 min ago
            seenBy = listOf("bob")
        )
    ),
    lastUpdated = 0L
)

class ChatService(private val client: HttpClient) {

    suspend fun loadMetadata(teamId: String): ChatGroup {
        Log.d("Service", "loadNotifications")
        delay(500)
        return sampleTeam
    }
}