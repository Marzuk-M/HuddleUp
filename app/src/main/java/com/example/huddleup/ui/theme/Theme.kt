package com.example.huddleup.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Coral,
    onPrimary = colourWhite,
    secondary = DustyRose,
    onSecondary = CocoaBrown,
    background = Cream,
    onBackground = RosewoodText,
    surface = RoseQuartz,
    onSurface = BlushText,
    primaryContainer = LightPrimaryContainer,
    secondaryContainer = LightSecondaryContainer,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = CocoaBrown
)

// Optionally, define a dark theme here
private val DarkColorScheme = darkColorScheme(
    primary = Coral,
    onPrimary = colourBlack,
    secondary = DustyRose,
    onSecondary = colourWhite,
    background = CocoaBrown,
    onBackground = Cream,
    surface = RosewoodText,
    onSurface = Cream,
    primaryContainer = CocoaBrown,
    secondaryContainer = RosewoodText,
    surfaceVariant = BlushText,
    onSurfaceVariant = Cream
)
@Composable
fun HuddleUpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}