package com.example.huddleup.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HuddleUpTheme(
    themeViewModel: ThemeViewModel,
    content: @Composable () -> Unit
) {
    val colorScheme = if (themeViewModel.isDarkMode.value) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}