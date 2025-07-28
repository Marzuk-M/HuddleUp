package com.example.huddleup.teamsearch

import kotlinx.serialization.Serializable

// Data class to represent a team
data class Team(
    val id: String,
    val name: String,
    val members: Int,
    val membershipState: TeamMembershipState
)

// Enum to represent the user's membership state with a team
enum class TeamMembershipState {
    MEMBER,        // User is a member of the team
    REQUESTED,     // User has requested to join (legacy - no longer used)
    NOT_A_MEMBER   // User is not a member
}

@Serializable
data class TeamResponse(
    val id: String,
    val name: String,
    val members: Int,
    val membershipState: String
)

@Serializable
data class ApiResponse(
    val message: String? = null,
    val error: String? = null
)