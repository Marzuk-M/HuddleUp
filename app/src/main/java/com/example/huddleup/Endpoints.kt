package com.example.huddleup

object Endpoints {
    const val BASE_URL = "http://10.0.2.2:3000"

    // Auth Endpoints
    fun getSignupEndpoint() = "$BASE_URL/api/auth/signup"
    fun checkUsernameEndpoint(username: String) = "$BASE_URL/api/auth/username/$username"
    
    // Schedule Endpoints
    fun getScheduleGamesTestEndpoint() = "$BASE_URL/api/schedule/games/test"
    fun getGameDetailsTestEndpoint(gameId: String) = "$BASE_URL/api/schedule/games/$gameId/test"
    fun getUpdateAvailabilityTestEndpoint(gameId: String) = "$BASE_URL/api/schedule/games/$gameId/availability/test"
}