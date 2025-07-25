package com.example.huddleup

object Endpoints {
    private const val BASE_URL = "http://10.0.2.2:3000"

    // Auth Endpoints
    fun getSignupEndpoint() = "$BASE_URL/api/auth/signup"
    fun checkUsernameEndpoint(username: String) = "$BASE_URL/api/auth/username/$username"
}