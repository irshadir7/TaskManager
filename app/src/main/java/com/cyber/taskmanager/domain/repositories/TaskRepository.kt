package com.cyber.taskmanager.domain.repositories

import com.cyber.taskmanager.data.local.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    suspend fun insertTask(task: Task)
    suspend fun getTasksByDate(selectedDate: LocalDate): Flow<List<Task>>
    suspend fun getTaskById(taskId: Int): Task
    fun getAllTasks(): Flow<List<Task>>
    suspend fun updateStatus(taskId: Int, status: String)
    suspend fun deleteTask(task: Task)


}