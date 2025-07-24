package com.example.huddleup.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel : ViewModel() {

    private val _notifications = MutableStateFlow<Map<String, List<Notification>>>(emptyMap())
    val notifications: StateFlow<Map<String, List<Notification>>> = _notifications
    init {
        fetchNotifications()
    }
    private fun fetchNotifications() {
        viewModelScope.launch {
            val result = MockNotificationApi.fetchNotifications()
            _notifications.value = result
        }
    }
}
