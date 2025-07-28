package com.example.huddleup.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// 🎨 Softer 3-Color Theme
val CreamWhite = Color(0xFFFFFBFE)    // Light background
val SkyBlue = Color(0xFF5A9EE6)       // Primary (softer blue)
val SoftGrayBlue = Color(0xFFE6EEF6)  // Surface (cards/TextField bg)
val AccentGray = Color(0xFF9CA9B5)    // Outline and hints

val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = Color.White,

    background = CreamWhite,
    onBackground = Color(0xFF222222),

    surface = SoftGrayBlue,
    onSurface = Color(0xFF111111),

    outline = AccentGray,
    onSurfaceVariant = Color(0xFF222222),
    surfaceVariant = SoftGrayBlue,
    tertiary = SkyBlue
)

val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    onPrimary = Color(0xFF0F0F0F),

    background = Color(0xFF1A1A1A),      // softer dark
    onBackground = Color(0xFFF5F5F5),    // off-white

    surface = Color(0xFF242424),         // dark gray surface
    onSurface = Color(0xFFEAEAEA),       // softer white text

    outline = Color(0xFF7B8995),         // muted gray outline
    onSurfaceVariant = Color(0xFFF0F0F0),
    surfaceVariant = Color(0xFF2E2E2E),
    tertiary = SkyBlue
)
