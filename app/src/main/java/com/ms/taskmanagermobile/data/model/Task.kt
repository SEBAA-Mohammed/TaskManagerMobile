package com.ms.taskmanagermobile.data.model

import java.time.LocalDateTime

// Le Task model :
data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val dueDate: LocalDateTime? = null
) 