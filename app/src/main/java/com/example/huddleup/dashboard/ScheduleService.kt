package com.example.huddleup.dashboard

import android.util.Log
import com.example.huddleup.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

class ScheduleService(private val client: HttpClient) {

    suspend fun getAllGames(): List<Game> {
        Log.d("ScheduleService", "Getting all games")
        return try {
            val response = client.get(Endpoints.getScheduleGamesTestEndpoint())
            val games: List<GameResponse> = response.body()
            Log.d("ScheduleService", "API returned ${games.size} games")
            
            games.map { gameResponse ->
                Game(
                    id = gameResponse.id.hashCode(), // Convert string ID to integer
                    originalId = gameResponse.id, // Store original string ID
                    team1 = gameResponse.teamName,
                    team2 = gameResponse.opponent,
                    date = LocalDate.parse(gameResponse.date),
                    time = gameResponse.time,
                    place = gameResponse.place
                )
            }
        } catch (e: Exception) {
            Log.e("ScheduleService", "Error getting games: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getGameDetails(gameId: String): Game? {
        Log.d("ScheduleService", "Getting game details for: $gameId")
        return try {
            val response = client.get(Endpoints.getGameDetailsTestEndpoint(gameId))
            val gameResponse: GameResponse = response.body()
            
            Game(
                id = gameResponse.id.hashCode(), // Convert string ID to integer
                originalId = gameResponse.id, // Store original string ID
                team1 = gameResponse.teamName,
                team2 = gameResponse.opponent,
                date = LocalDate.parse(gameResponse.date),
                time = gameResponse.time,
                place = gameResponse.place
            )
        } catch (e: Exception) {
            Log.e("ScheduleService", "Error getting game details: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun updateGameAvailability(gameId: String, status: String, userId: String? = null): Boolean {
        Log.d("ScheduleService", "Updating availability: gameId=$gameId, status=$status, userId=$userId")
        return try {
            val request = AvailabilityUpdateRequest(status = status, userId = userId)
            val response = client.put(Endpoints.getUpdateAvailabilityTestEndpoint(gameId)) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            val apiResponse: ApiResponse = response.body()
            Log.d("ScheduleService", "Availability updated: ${apiResponse.message}")
            true
        } catch (e: Exception) {
            Log.e("ScheduleService", "Error updating availability: ${e.message}")
            e.printStackTrace()
            false
        }
    }
} 