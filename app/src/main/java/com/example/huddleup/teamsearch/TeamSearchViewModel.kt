package com.example.huddleup.teamsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamSearchViewModel : ViewModel() {

    private val teamSearchService = TeamSearchService(HttpClientProvider.httpClient)

    private val _searchResults = MutableStateFlow<List<Team>>(emptyList())
    val searchResults: StateFlow<List<Team>> get() = _searchResults

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun searchTeams(searchQuery: String) {
        if (searchQuery.isEmpty()) {
            viewModelScope.launch {
                _searchResults.value = listOf()
                _isLoading.value = false
            }
        } else {
            viewModelScope.launch {
                _isLoading.value = true
                _searchResults.value = teamSearchService.searchTeams(searchQuery = searchQuery)
                _isLoading.value = false
            }
        }
    }

    private fun refreshTeamsList(searchQuery: String) {
        viewModelScope.launch {
            _searchResults.value = teamSearchService.searchTeams(searchQuery)
        }
    }

    fun sendJoinRequest(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            teamSearchService.sendJoinRequest(teamID)
            refreshTeamsList(searchQuery)
        }
    }

    fun unsendJoinRequest(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            teamSearchService.unsendJoinRequest(teamID)
            refreshTeamsList(searchQuery)
        }
    }

    fun leaveTeam(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            teamSearchService.leaveTeam(teamID)
            refreshTeamsList(searchQuery)
        }
    }
}
