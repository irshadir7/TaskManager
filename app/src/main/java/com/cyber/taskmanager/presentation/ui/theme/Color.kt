package com.cyber.taskmanager.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorPalette(
    val Black: Color,
    val White: Color,
    val Grey: Color,
    val GreyLight: Color,
    val Blue: Color,
    val TextColor: Color,
)

val DarkColors = AppColorPalette(
    Black = Color(0xFF1b1b1b),
    White = Color(0xFFFFFFFF),
    Grey = Color(0xFF313131),
    GreyLight = Color(0xFFF2F2F2),
    Blue = Color(0xFFb5cff8),
    TextColor = Color(0xFF000000),
    )

val LightColors = AppColorPalette(
    Black = Color(0xFFFFFFFF), // Inverted meaning for light theme
    White = Color(0xFF000000),
    Grey = Color(0xFFCCCCCC),
    GreyLight = Color(0xFFF2F2F2),
    Blue = Color(0xFF1E88E5),
    TextColor = Color(0xFF000000),
    )

val LocalAppColors = staticCompositionLocalOf { DarkColors } // Default
