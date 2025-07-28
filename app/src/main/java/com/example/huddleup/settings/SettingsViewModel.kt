package com.example.huddleup.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val settingsService = SettingsService(HttpClientProvider.httpClient)

    private val _name = MutableStateFlow("John Doe")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("john.doe@email.com")
    val email: StateFlow<String> = _email

    private val _username = MutableStateFlow("john_doe")
    val username: StateFlow<String> = _username

    private val _memberSince = MutableStateFlow("Jan 2000")
    val memberSince: StateFlow<String> = _memberSince

    private val _notificationEnabled = MutableStateFlow(true)
    val notificationEnabled: StateFlow<Boolean> = _notificationEnabled

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingNotif = MutableStateFlow(false)
    val isLoadingNotif: StateFlow<Boolean> = _isLoadingNotif

    private val _isLoadingName = MutableStateFlow(false)
    val isLoadingName: StateFlow<Boolean> = _isLoadingName

    fun getUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profile = settingsService.getUserProfile()
                _name.value = profile.name
                _email.value = profile.email
                _username.value = profile.username
                _memberSince.value = profile.memberSince
                _notificationEnabled.value = profile.notificationEnabled
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateName(newName: String) {
        if (newName != _name.value) {
            viewModelScope.launch {
                _isLoadingName.value = true
                try {
                    settingsService.updateUserName(newName)
                    _name.value = newName
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoadingName.value = false
                }
            }
        }
    }

    fun updateNotification(enabled: Boolean) {
        if (enabled != _notificationEnabled.value) {
            viewModelScope.launch {
                _isLoadingNotif.value = true
                try {
                    settingsService.updateNotification(enabled)
                    _notificationEnabled.value = enabled
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoadingNotif.value = false
                }
            }
        }
    }
}