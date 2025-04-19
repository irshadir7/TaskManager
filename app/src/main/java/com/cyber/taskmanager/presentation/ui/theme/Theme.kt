package com.cyber.taskmanager.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun TaskManagementAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkColors else LightColors

    val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) {
            darkColorScheme(
                primary = appColors.Black,
                onPrimary = appColors.White,
                secondary = appColors.Grey,
                onSecondary = appColors.White,
                background = appColors.Black,
                onBackground = appColors.White,
                surface = appColors.GreyLight,
                onSurface = appColors.White,
                tertiary = appColors.Blue
            )
        } else {
            lightColorScheme(
                primary = appColors.White,
                onPrimary = appColors.Black,
                secondary = appColors.Blue,
                onSecondary = appColors.Black,
                background = appColors.White,
                onBackground = appColors.Black,
                surface = Color(0xFFF2F2F2),
                onSurface = appColors.Black,
                tertiary = appColors.Blue
            )
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
