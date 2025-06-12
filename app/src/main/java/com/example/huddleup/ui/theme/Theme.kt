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

// Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = colourMedium,
    onPrimary = colourWhite,
    secondary = colourLight3,
    onSecondary = colourWhite,
    tertiary = colourLight2,
    onTertiary = colourBlack,
    background = colourLight1,
    onBackground = colourDark1,
    surface = colourWhite,
    onSurface = colourDark2
)

// Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = colourLight2,
    onPrimary = colourDark1,
    secondary = colourLight3,
    onSecondary = colourDark2,
    tertiary = colourMedium,
    onTertiary = colourWhite,
    background = colourDark1,
    onBackground = colourLight1,
    surface = colourDark2,
    onSurface = colourLight2
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