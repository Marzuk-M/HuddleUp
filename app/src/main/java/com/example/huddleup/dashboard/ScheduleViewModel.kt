package com.example.huddleup.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ScheduleViewModel : ViewModel() {

    private val scheduleService = ScheduleService(HttpClientProvider.httpClient)

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.of(2025, 7, 29))
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    init {
        loadGames()
    }

    fun loadGames() {
        viewModelScope.launch {
            _isLoading.value = true
            val gamesList = scheduleService.getAllGames()
            _games.value = gamesList
            _isLoading.value = false
        }
    }

    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun getGamesForDate(date: LocalDate): List<Game> {
        return _games.value.filter { it.date == date }
    }

    fun updateGameAvailability(gameId: String, status: String, userId: String? = null) {
        viewModelScope.launch {
            val success = scheduleService.updateGameAvailability(gameId, status, userId)
            if (success) {
                // Refresh games after updating availability
                loadGames()
            }
        }
    }

    fun getNextGame(): Game? {
        val today = LocalDate.now()
        val upcomingGames = _games.value.filter { it.date >= today }
        return upcomingGames.minByOrNull { it.date }
    }
} 