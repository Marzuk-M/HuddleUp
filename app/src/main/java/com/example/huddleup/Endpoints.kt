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

    // Team Endpoints
    fun getTeamSearchEndpoint() = "$BASE_URL/api/teams/search"
    fun getTeamSearchTestEndpoint() = "$BASE_URL/api/teams/search/test"
    fun getTeamDetailsEndpoint(teamId: String) = "$BASE_URL/api/teams/$teamId"
    fun getJoinTeamEndpoint(teamId: String) = "$BASE_URL/api/teams/$teamId/join"
    fun getJoinTeamTestEndpoint(teamId: String) = "$BASE_URL/api/teams/$teamId/join/test"
    fun getLeaveTeamEndpoint(teamId: String) = "$BASE_URL/api/teams/$teamId/leave"
    fun getLeaveTeamTestEndpoint(teamId: String) = "$BASE_URL/api/teams/$teamId/leave/test"
    fun getMyTeamsEndpoint() = "$BASE_URL/api/teams/my-teams"
    fun getMyTeamsTestEndpoint() = "$BASE_URL/api/teams/my-teams/test"

    // Settings Endpoints
    fun getProfileEndpoint() = "$BASE_URL/api/settings/profile"
    fun updateNameEndpoint() = "$BASE_URL/api/settings/name"
    fun updateNotificationEndpoint() = "$BASE_URL/api/settings/notifications"
  
    // Notifications Endpoints
    fun getNotificationsEndpoint() = "$BASE_URL/api/notifications"
    fun markNotificationAsReadEndpoint(notificationId: String) = "$BASE_URL/api/notifications/$notificationId/read"
    fun createNotificationEndpoint() = "$BASE_URL/api/notifications"

}