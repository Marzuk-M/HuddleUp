package com.example.huddleup.teamdetails

import com.example.huddleup.chat.User

data class TeamDetails(
    val teamId: String,
    val teamName: String,
    val sport: String,
    val description: String? = null,
    val createdAt: Long,
    val admins: List<User>,
    val members: List<User>,
    val pastGames: List<GameResult> = emptyList()
)

data class GameResult(
    val opponent: String,
    val date: Long,
    val result: GameOutcome,
    val teamScore: Int,
    val opponentScore: Int
)

enum class GameOutcome {
    WON,
    LOST,
    DRAW
}