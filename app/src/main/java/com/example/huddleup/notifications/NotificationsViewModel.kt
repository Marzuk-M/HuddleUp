package com.example.huddleup.notifications

import com.example.huddleup.HttpClientProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {
    private val notificationsService = NotificationsService(HttpClientProvider.httpClient)

    private val _results = MutableStateFlow<List<NotificationBlock>>(emptyList())
    val results: StateFlow<List<NotificationBlock>> get() = _results

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = notificationsService.loadNotifications()
            _results.value = result
            _isLoading.value = false
        }
    }
}
