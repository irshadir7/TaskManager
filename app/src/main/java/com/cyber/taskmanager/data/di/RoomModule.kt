package com.cyber.taskmanager.data.di

import android.content.Context
import androidx.room.Room
import com.cyber.taskmanager.domain.usecase.ValidateTaskUseCase
import com.cyber.taskmanager.data.local.TaskDao
import com.cyber.taskmanager.data.local.TaskDatabase
import com.cyber.taskmanager.data.repositories.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun getRepository(dao: TaskDao): TaskRepositoryImpl {
        return TaskRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun getDao(database: TaskDatabase): TaskDao {
        return database.taskDao
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database"
        ).build()

    @Singleton
    @Provides
    fun getValidateTaskUseCase(): ValidateTaskUseCase {
        return ValidateTaskUseCase()
    }
}