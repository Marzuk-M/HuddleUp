package com.example.huddleup.teamdetails

import android.util.Log
import com.example.huddleup.Endpoints
import com.example.huddleup.chat.User
import com.example.huddleup.teamsearch.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import kotlinx.coroutines.delay

val sampleTeamDetails = TeamDetails(
    teamId = "team_001",
    teamName = "Waterloo Warriors",
    sport = "Football",
    description = "We are a group of students who love playing badminton every weekend!",
    createdAt = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30, // ~30 days ago
    admins = listOf(
        User(username = "sarah01", name = "Sarah Kim", profileImageUrl = null)
    ),
    members = listOf(
        User(username = "sarah01", name = "Sarah Kim", profileImageUrl = null),
        User(username = "daniel02", name = "Daniel Wu", profileImageUrl = null),
        User(username = "julia33", name = "Julia Tran", profileImageUrl = "https://example.com/profiles/julia.jpg"),
    ),
    pastGames = listOf(
        GameResult(
            opponent = "teamID_2",
            date = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30, // ~30 days ago,
            result = GameOutcome.WON,
            teamScore = 10,
            opponentScore = 5
        ),
        GameResult(
            opponent = "teamID_3",
            date = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 15, // ~15 days ago,
            result = GameOutcome.LOST,
            teamScore = 5,
            opponentScore = 100
        ),
        GameResult(
            opponent = "teamID_4",
            date = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 5, // ~5 days ago,
            result = GameOutcome.DRAW,
            teamScore = 20,
            opponentScore = 20
        ),
    )
)


class TeamDetailsService(private val client: HttpClient) {

    suspend fun getTeamDetails(teamId: String): TeamDetails {
        Log.d("Service", "getMyTeams")
        delay(500)
        return sampleTeamDetails
    }

    suspend fun leaveTeam(teamID: String): Boolean {
        Log.d("Service", "leaveTeam: $teamID")

        return try {
            // Temporarily use test endpoint (no auth required)
            val response = client.delete(Endpoints.getLeaveTeamTestEndpoint(teamID))

            val apiResponse: ApiResponse = response.body()
            apiResponse.message != null
        } catch (e: Exception) {
            Log.e("Service", "Error leaving team: ${e.message}")
            false
        }
    }
}