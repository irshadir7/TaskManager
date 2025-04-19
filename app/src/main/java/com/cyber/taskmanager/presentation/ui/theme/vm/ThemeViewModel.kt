package com.cyber.taskmanager.presentation.ui.theme.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cyber.taskmanager.data.local.ThemePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val themePref = ThemePreference(application)

    private val _isDark = MutableStateFlow(false)
    val isDark: StateFlow<Boolean> get() = _isDark

    init {
        viewModelScope.launch {
            themePref.isDarkMode.collect {
                _isDark.value = it
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themePref.saveDarkMode(!_isDark.value)
        }
    }
}
