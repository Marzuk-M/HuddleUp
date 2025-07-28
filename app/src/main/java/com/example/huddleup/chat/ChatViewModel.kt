package com.example.huddleup.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val chatService = ChatService(HttpClientProvider.httpClient)

    // Team Metadata
    private val _teamName = MutableStateFlow("")
    val teamName: StateFlow<String> get() = _teamName

    private val _members = MutableStateFlow<List<User>>(emptyList())
    val members: StateFlow<List<User>> get() = _members

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val _lastUpdated = MutableStateFlow(0L)
    val lastUpdated: StateFlow<Long> get() = _lastUpdated

    // Current User
    private val _currentUsername = MutableStateFlow("")
    val currentUsername: StateFlow<String> get() = _currentUsername

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadMetadata(teamId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val team = chatService.loadMetadata(teamId)
            _teamName.value = team.teamName
            _members.value = team.members
            _messages.value = team.messages
            _lastUpdated.value = team.lastUpdated
            _isLoading.value = false
        }
    }

}
