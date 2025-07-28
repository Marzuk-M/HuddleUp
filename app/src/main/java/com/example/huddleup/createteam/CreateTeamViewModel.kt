package com.example.huddleup.createteam

import androidx.lifecycle.ViewModel
import com.example.huddleup.HttpClientProvider

class CreateTeamViewModel : ViewModel() {
    private val createTeamService = CreateTeamService(HttpClientProvider.httpClient)

    fun createTeam(
        name: String,
        description: String,
        sport: String,
        onSuccess: () -> Boolean,
        onError: () -> Unit
    ) {

    }
}
