package com.example.huddleup.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ------------------------------- Colours --------------------------------- //

// Basic Colours
val colourWhite = Color.White
val colourBlack = Color.Black

// Base palette
val Cream = Color(0xFFF9F1F0)
val RoseQuartz = Color(0xFFFADCD9)
val DustyRose = Color(0xFFF8AFA6)
val Coral = Color(0xFFF79489)

// Soft cocoa / rosewood tones for text contrast
val CocoaBrown = Color(0xFF5C3A38)
val RosewoodText = Color(0xFF6E4C4A)
val BlushText = Color(0xFF4A3B3B)

// Light container shades
val LightPrimaryContainer = Color(0xFFFDD7D3)
val LightSecondaryContainer = Color(0xFFFCE9E7)
val SurfaceVariantLight = Color(0xFFF7E4E2)

// ---------------------------- Colour Schemes ----------------------------- //

// Light colour scheme
val LightColorScheme = lightColorScheme(
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

// Dark colour scheme
val DarkColorScheme = darkColorScheme(
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