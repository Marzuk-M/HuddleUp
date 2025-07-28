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
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("TeamSearchViewModel", "Searching teams with query: '$searchQuery'")
            
            if (searchQuery.isEmpty()) {
                // Show all teams when search is empty
                val results = teamSearchService.searchTeams("")
                android.util.Log.d("TeamSearchViewModel", "Empty query results: ${results.size} teams")
                _searchResults.value = results
            } else {
                // Filter teams based on search query
                val results = teamSearchService.searchTeams(searchQuery = searchQuery)
                android.util.Log.d("TeamSearchViewModel", "Query '$searchQuery' results: ${results.size} teams")
                _searchResults.value = results
            }
            _isLoading.value = false
        }
    }

    private fun refreshTeamsList(searchQuery: String) {
        viewModelScope.launch {
            // Always refresh, regardless of search query
            _searchResults.value = teamSearchService.searchTeams(searchQuery)
        }
    }

    fun sendJoinRequest(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            val success = teamSearchService.sendJoinRequest(teamID)
            if (success) {
                refreshTeamsList(searchQuery)
            }
        }
    }

    // Note: unsendJoinRequest is no longer needed since users join instantly
    // Keeping this for backward compatibility but it won't be used
    fun unsendJoinRequest(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            val success = teamSearchService.unsendJoinRequest(teamID)
            if (success) {
                refreshTeamsList(searchQuery)
            }
        }
    }

    fun leaveTeam(teamID: String, searchQuery: String) {
        viewModelScope.launch {
            val success = teamSearchService.leaveTeam(teamID)
            if (success) {
                refreshTeamsList(searchQuery)
            }
        }
    }
}
