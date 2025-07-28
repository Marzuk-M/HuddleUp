package com.example.huddleup.dashboard

import kotlinx.serialization.Serializable
import java.time.LocalDate

// Game model with time
data class Game(
    val id: Int,
    val originalId: String, // Store the original string ID from API
    val team1: String,
    val team2: String,
    val date: LocalDate,
    val time: String,
    val place: String
)

@Serializable
data class GameResponse(
    val id: String,
    val teamId: String,
    val teamName: String,
    val opponent: String,
    val date: String,
    val time: String,
    val place: String,
    val availability: AvailabilityResponse
)

@Serializable
data class AvailabilityResponse(
    val `in`: List<String>,
    val out: List<String>,
    val maybe: List<String>
)

@Serializable
data class AvailabilityUpdateRequest(
    val status: String,
    val userId: String? = null
)

@Serializable
data class ApiResponse(
    val message: String
)