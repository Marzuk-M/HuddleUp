package com.example.huddleup.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huddleup.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val settingsService = SettingsService(HttpClientProvider.httpClient)

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _memberSince = MutableStateFlow("")
    val memberSince: StateFlow<String> = _memberSince

    private val _notificationEnabled = MutableStateFlow(true)
    val notificationEnabled: StateFlow<Boolean> = _notificationEnabled

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingNotif = MutableStateFlow(false)
    val isLoadingNotif: StateFlow<Boolean> = _isLoadingNotif

    private val _isLoadingName = MutableStateFlow(false)
    val isLoadingName: StateFlow<Boolean> = _isLoadingName

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val profile = settingsService.getUserProfile()
                _name.value = profile.name
                _email.value = profile.email
                _username.value = profile.username
                _memberSince.value = profile.memberSince
                _notificationEnabled.value = profile.notificationEnabled
                Log.d("SettingsViewModel", "Profile loaded successfully")
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Error loading profile", e)
                _errorMessage.value = "Failed to load profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateName(newName: String) {
        if (newName.isNotBlank() && newName != _name.value) {
            viewModelScope.launch {
                _isLoadingName.value = true
                _errorMessage.value = null
                try {
                    settingsService.updateUserName(newName)
                    _name.value = newName
                    Log.d("SettingsViewModel", "Name updated successfully")
                } catch (e: Exception) {
                    Log.e("SettingsViewModel", "Error updating name", e)
                    _errorMessage.value = "Failed to update name: ${e.message}"
                } finally {
                    _isLoadingName.value = false
                }
            }
        }
    }

    fun updateNotification(enabled: Boolean) {
        viewModelScope.launch {
            _isLoadingNotif.value = true
            _errorMessage.value = null
            try {
                settingsService.updateNotification(enabled)
                _notificationEnabled.value = enabled
                Log.d("SettingsViewModel", "Notification setting updated successfully")
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Error updating notification setting", e)
                _errorMessage.value = "Failed to update notification setting: ${e.message}"
                // Revert the UI state if the update failed
                _notificationEnabled.value = !enabled
            } finally {
                _isLoadingNotif.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}