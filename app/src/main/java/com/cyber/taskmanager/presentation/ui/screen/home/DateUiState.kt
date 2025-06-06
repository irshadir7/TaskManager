package com.task.hub.presentation.ui.screen.home

import com.cyber.taskmanager.data.local.Task
import java.time.LocalDate

data class DateUiState(
    var selectedDate: LocalDate = LocalDate.now(),
    var listTasks: List<Task> = emptyList(),
    var hasTasks: Boolean = true
)