package com.example.huddleup.notifications

import com.example.huddleup.HttpClientProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class NotificationsViewModel : ViewModel() {
    private val notificationsService = NotificationsService(HttpClientProvider.httpClient)

    private val _results = MutableStateFlow<List<NotificationBlock>>(emptyList())
    val results: StateFlow<List<NotificationBlock>> get() = _results

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Get the current user's auth token
                val currentUser = FirebaseAuth.getInstance().currentUser
                val authToken = currentUser?.let { user ->
                    try {
                        user.getIdToken(false).await()?.token
                    } catch (e: Exception) {
                        null
                    }
                }
                
                val result = notificationsService.loadNotifications(authToken)
                _results.value = result
            } catch (e: Exception) {
                _error.value = "Failed to load notifications: ${e.message}"
                _results.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
