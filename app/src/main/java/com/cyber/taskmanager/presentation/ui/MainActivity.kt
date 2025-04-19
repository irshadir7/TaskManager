package com.cyber.taskmanager.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.cyber.taskmanager.data.local.ThemePreference
import com.cyber.taskmanager.presentation.ui.screen.navigation.ParentNav
import com.cyber.taskmanager.presentation.ui.theme.TaskManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var themePreference: ThemePreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themePreference = ThemePreference(applicationContext)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val isDarkMode by themePreference.isDarkMode.collectAsState(initial = false)
            TaskManagementAppTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ParentNav()
                }
            }
        }
    }
}
