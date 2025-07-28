package com.example.huddleup.myteams

// Data class to represent a team
data class Team(
    val id: String,
    val name: String,
    val members: Int,
    val membershipState: TeamMembershipState
)

// Enum to represent the user's membership state with a team
enum class TeamMembershipState {
    MEMBER,
    REQUESTED,
    NOT_A_MEMBER
}
