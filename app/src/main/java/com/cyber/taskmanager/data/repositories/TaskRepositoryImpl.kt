package com.cyber.taskmanager.data.repositories

import com.cyber.taskmanager.data.local.Task
import com.cyber.taskmanager.domain.repositories.TaskRepository
import com.cyber.taskmanager.data.local.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao): TaskRepository {

    override suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    override suspend fun getTasksByDate(selectedDate: LocalDate): Flow<List<Task>> {
        return taskDao.getTasksByDate(selectedDate)
    }

    override suspend fun getTaskById(taskId: Int): Task = withContext(Dispatchers.IO) {
        taskDao.getTaskById(taskId)
    }

    override  fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun updateStatus(taskId: Int, status: String) {
        withContext(Dispatchers.IO) {
            taskDao.updateStatus(taskId, status)
        }
    }

    override suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }



}