package com.example.huddleup.ui.theme

import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.lightColorScheme
import com.example.huddleup.ui.theme.DarkColorScheme
import com.example.huddleup.ui.theme.LightColorScheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HuddleUpTheme(
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val dark = themeViewModel.darkTheme.collectAsStateWithLifecycle().value
    val colors = if (dark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}