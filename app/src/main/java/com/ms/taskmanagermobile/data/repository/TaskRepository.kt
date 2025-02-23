package com.ms.taskmanagermobile.data.repository

import com.ms.taskmanagermobile.data.api.RetrofitClient
import com.ms.taskmanagermobile.data.model.Task

// Traitement des requete en utilisant l'instand de taskapi
class TaskRepository {
    private val api = RetrofitClient.taskApi

    suspend fun getTasks(): List<Task> = api.getTasks()
    
    suspend fun createTask(task: Task): Task = api.createTask(task)
    
    suspend fun updateTask(task: Task): Task = api.updateTask(task.id, task)
    
    suspend fun deleteTask(taskId: Int) = api.deleteTask(taskId)
} 