package com.example.huddleup.teamsearch

import android.util.Log
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

// Dummy data for teams
// TODO: Remove this once API returns real data
val sampleTeams = listOf(
    Team("212231", "Team", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212232", "Team A", 15, TeamMembershipState.MEMBER),
    Team("212233", "Team B", 15, TeamMembershipState.REQUESTED),
    Team("212234", "Team C", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212235", "Team D", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212231", "Team", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212232", "Team A", 15, TeamMembershipState.MEMBER),
    Team("212233", "Team B", 15, TeamMembershipState.REQUESTED),
    Team("212234", "Team C", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212235", "Team D", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212231", "Team", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212232", "Team A", 15, TeamMembershipState.MEMBER),
    Team("212233", "Team B", 15, TeamMembershipState.REQUESTED),
    Team("212234", "Team C", 15, TeamMembershipState.NOT_A_MEMBER),
    Team("212235", "Team D", 15, TeamMembershipState.NOT_A_MEMBER),
)

class TeamSearchService(private val client: HttpClient) {

    suspend fun searchTeams(searchQuery: String): List<Team> {
        Log.d("Service", "searchTeams: $searchQuery")
        delay(500)
        return sampleTeams
    }

    suspend fun sendJoinRequest(teamID: String): Boolean {
        Log.d("Service", "sendJoinRequest: $teamID")
        delay(500)
        return true
    }

    suspend fun unsendJoinRequest(teamID: String): Boolean {
        Log.d("Service", "unsendJoinRequest: $teamID")
        delay(500)
        return true
    }

    suspend fun leaveTeam(teamID: String): Boolean {
        Log.d("Service", "leaveTeam: $teamID")
        delay(500)
        return true
    }
}