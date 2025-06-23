package com.example.huddleup.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel: ViewModel() {
    var isDarkMode = mutableStateOf(false)
    private set

    fun toggleTheme() {
        viewModelScope.launch {
            isDarkMode.value = !isDarkMode.value
        }
    }
}