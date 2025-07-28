package com.example.huddleup.teamsearch

import android.util.Log
import com.example.huddleup.Endpoints
import com.example.huddleup.auth.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.http.headers
import kotlinx.serialization.Serializable

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

class TeamSearchService(private val client: HttpClient) {

    private suspend fun getAuthHeaders(): Map<String, String> {
        val token = AuthService.getFirebaseAuthToken()
        return if (token != null) {
            mapOf("Authorization" to "Bearer $token")
        } else {
            emptyMap()
        }
    }

    suspend fun searchTeams(searchQuery: String): List<Team> {
        Log.d("Service", "searchTeams: $searchQuery")
        
        return try {
            // Temporarily use test endpoint (no auth required)
            val response = client.get(Endpoints.getTeamSearchTestEndpoint()) {
                url {
                    parameters.append("q", searchQuery)
                }
            }
            
            val teams: List<TeamResponse> = response.body()
            Log.d("Service", "API returned ${teams.size} teams")
            
            teams.map { teamResponse ->
                Team(
                    id = teamResponse.id,
                    name = teamResponse.name,
                    members = teamResponse.members,
                    membershipState = when (teamResponse.membershipState) {
                        "MEMBER" -> TeamMembershipState.MEMBER
                        "REQUESTED" -> TeamMembershipState.REQUESTED
                        else -> TeamMembershipState.NOT_A_MEMBER
                    }
                )
            }
        } catch (e: Exception) {
            Log.e("Service", "Error searching teams: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun sendJoinRequest(teamID: String): Boolean {
        Log.d("Service", "sendJoinRequest: $teamID")
        
        return try {
            // Temporarily use test endpoint (no auth required)
            val response = client.post(Endpoints.getJoinTeamTestEndpoint(teamID)) {
                contentType(ContentType.Application.Json)
            }
            
            val apiResponse: ApiResponse = response.body()
            apiResponse.message != null
        } catch (e: Exception) {
            Log.e("Service", "Error sending join request: ${e.message}")
            false
        }
    }

    suspend fun unsendJoinRequest(teamID: String): Boolean {
        Log.d("Service", "unsendJoinRequest: $teamID")
        
        return try {
            // Temporarily use test endpoint (no auth required)
            val response = client.delete(Endpoints.getJoinTeamTestEndpoint(teamID))
            
            val apiResponse: ApiResponse = response.body()
            apiResponse.message != null
        } catch (e: Exception) {
            Log.e("Service", "Error canceling join request: ${e.message}")
            false
        }
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