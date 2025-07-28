package com.example.huddleup.myteams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyTeamsViewModel : ViewModel() {
    private val myTeamsService = MyTeamsService(HttpClientProvider.httpClient)

    private val _myTeams = MutableStateFlow<List<Team>>(emptyList())
    val myTeams: StateFlow<List<Team>> get() = _myTeams

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun getMyTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            _myTeams.value = myTeamsService.getMyTeams()
            _isLoading.value = false
        }
    }

    fun leaveTeam(teamID: String) {
        viewModelScope.launch {
            myTeamsService.leaveTeam(teamID)
        }
    }
}