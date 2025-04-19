package com.cyber.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cyber.taskmanager.data.local.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE dueDate =:selectedDate")
    fun getTasksByDate(selectedDate: LocalDate): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id =:taskId")
    fun getTaskById(taskId: Int): Task

    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("UPDATE task SET status = :status WHERE id = :taskId")
    suspend fun updateStatus(taskId: Int, status: String)


}