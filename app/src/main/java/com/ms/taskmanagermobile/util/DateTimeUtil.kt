package com.ms.taskmanagermobile.util

import java.time.Duration
import java.time.LocalDateTime

object DateTimeUtil {
    fun getTimeLeft(dueDate: LocalDateTime): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(now, dueDate)
        
        return when {
            duration.isNegative -> "En retard!"
            duration.toHours() < 1 -> "${duration.toMinutes()} minutes"
            duration.toDays() < 1 -> "${duration.toHours()} heures"
            duration.toDays() < 30 -> "${duration.toDays()} jours"
            else -> "${duration.toDays() / 30} mois"
        }
    }

    fun isUrgent(dueDate: LocalDateTime): Boolean {
        val now = LocalDateTime.now()
        val duration = Duration.between(now, dueDate)
        return !duration.isNegative && duration.toHours() < 1
    }
} 