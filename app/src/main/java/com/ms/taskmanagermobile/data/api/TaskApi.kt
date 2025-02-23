package com.ms.taskmanagermobile.data.api

import com.ms.taskmanagermobile.data.model.Task
import retrofit2.http.*

interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
    
    @POST("tasks")
    suspend fun createTask(@Body task: Task): Task
    
    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: Task): Task
    
    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
} 