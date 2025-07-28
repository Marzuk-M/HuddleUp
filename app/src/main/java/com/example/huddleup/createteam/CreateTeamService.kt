package com.example.huddleup.createteam

import android.util.Log
import io.ktor.client.HttpClient

class CreateTeamService(private val client: HttpClient) {

    suspend fun createTeam(): Boolean {
        Log.d("Service", "createTeam")
        return true
    }
}
