package com.example.huddleup.teamdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamDetailsViewModel : ViewModel() {
    private val teamDetailsService = TeamDetailsService(HttpClientProvider.httpClient)

    private val _teamDetails = MutableStateFlow<TeamDetails?>(null)
    val teamDetails: StateFlow<TeamDetails?> = _teamDetails

    private val _teamName = MutableStateFlow("")
    val teamName: StateFlow<String> = _teamName

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadTeamDetails(teamId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val details = teamDetailsService.getTeamDetails(teamId)
                _teamDetails.value = details
                _teamName.value = details.teamName
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load team details"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun leaveTeam(teamId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val success = teamDetailsService.leaveTeam(teamId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load team details"
            } finally {
                _isLoading.value = false
            }
        }
    }
}