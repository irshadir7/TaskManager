package com.cyber.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cyber.taskmanager.data.convertors.ListConvertor
import com.cyber.taskmanager.data.convertors.LocalDateConverter

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class, ListConvertor::class)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
}