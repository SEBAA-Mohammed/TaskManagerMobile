package com.ms.taskmanagermobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ms.taskmanagermobile.data.model.Task
import com.ms.taskmanagermobile.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

// Gèstion de la logique métier
class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()
    
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadTasks()
    }
    
    fun loadTasks() {
        viewModelScope.launch {
            try {
                _tasks.value = repository.getTasks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur lors du chargement des tâches: ${e.message}"
            }
        }
    }
    
    fun addTask(title: String, description: String, dueDate: LocalDateTime) {
        viewModelScope.launch {
            try {
                val newTask = Task(
                    title = title,
                    description = description,
                    dueDate = dueDate
                )
                repository.createTask(newTask)
                loadTasks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur lors de l'ajout de la tâche: ${e.message}"
            }
        }
    }
    
    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
                loadTasks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur lors de la mise à jour de la tâche: ${e.message}"
            }
        }
    }
    
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteTask(taskId)
                loadTasks()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erreur lors de la suppression de la tâche: ${e.message}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
} 