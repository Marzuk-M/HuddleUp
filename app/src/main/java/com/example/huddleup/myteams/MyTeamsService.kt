package com.example.huddleup.myteams

import android.util.Log
import com.example.huddleup.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get

class MyTeamsService(private val client: HttpClient) {

    suspend fun getMyTeams(): List<Team> {
        Log.d("Service", "getMyTeams")
        
        return try {
            val response = client.get(Endpoints.getMyTeamsTestEndpoint())
            val teams: List<TeamResponse> = response.body()
            
            teams.map { teamResponse ->
                Team(
                    id = teamResponse.id,
                    name = teamResponse.name,
                    members = teamResponse.members,
                    membershipState = TeamMembershipState.MEMBER
                )
            }
        } catch (e: Exception) {
            Log.e("Service", "Error getting my teams: ${e.message}")
            emptyList()
        }
    }

    suspend fun leaveTeam(teamID: String): Boolean {
        Log.d("Service", "leaveTeam: $teamID")
        
        return try {
            val response = client.delete(Endpoints.getLeaveTeamTestEndpoint(teamID))
            val apiResponse: ApiResponse = response.body()
            apiResponse.message != null
        } catch (e: Exception) {
            Log.e("Service", "Error leaving team: ${e.message}")
            false
        }
    }
}