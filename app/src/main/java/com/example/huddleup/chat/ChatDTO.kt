package com.example.huddleup.chat

data class User(
    val username: String,
    val name: String,
    val profileImageUrl: String? = null
)

data class Message(
    val id: String,
    val sender: User,
    val content: String,
    val timestamp: Long,
    val seenBy: List<String> = emptyList() // usernames of those who saw it
)

data class ChatGroup(
    val teamId: String,
    val teamName: String,
    val members: List<User>,
    val messages: List<Message> = emptyList(),
    val lastUpdated: Long
)