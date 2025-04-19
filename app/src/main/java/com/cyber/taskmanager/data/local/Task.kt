package com.cyber.taskmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cyber.taskmanager.presentation.ui.screen.add.Member
import kotlinx.parcelize.RawValue
import java.time.LocalDate

@Entity (tableName = "Task")
data class Task(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate = LocalDate.now(),
    val estimateTime: Int = 0,
    val priority: String = "",
    val status: String = "Pending",
    val members: @RawValue List<Member> = emptyList()
)
