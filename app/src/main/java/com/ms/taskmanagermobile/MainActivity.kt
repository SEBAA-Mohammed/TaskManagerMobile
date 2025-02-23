package com.ms.taskmanagermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ms.taskmanagermobile.data.model.Task
import com.ms.taskmanagermobile.ui.components.*
import com.ms.taskmanagermobile.ui.theme.TaskManagerMobileTheme
import com.ms.taskmanagermobile.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerMobileTheme {
                TaskManagerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerApp(viewModel: TaskViewModel = viewModel()) {
    var currentRoute by remember { mutableStateOf("tasks") }
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    val tasks by viewModel.tasks.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(if (currentRoute == "tasks") "Tâches à faire" else "Tâches terminées") },
                actions = {
                    if (currentRoute == "tasks") {
                        IconButton(onClick = { showAddDialog = true }) {
                            Icon(Icons.Default.Add, "Ajouter une tâche")
                        }
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(currentRoute) { currentRoute = it } },
        snackbarHost = {
            error?.let { errorMessage ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("OK")
                        }
                    }
                ) { Text(errorMessage) }
            }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            when (currentRoute) {
                "tasks" -> {
                    LazyColumn {
                        items(tasks.filter { !it.isCompleted }) { task ->
                            TaskItem(
                                task = task,
                                onTaskClick = { viewModel.updateTask(it) },
                                onEditClick = { taskToEdit = it },
                                onDeleteClick = { viewModel.deleteTask(it.id) }
                            )
                        }
                    }
                }
                "completed" -> {
                    LazyColumn {
                        items(tasks.filter { it.isCompleted }) { task ->
                            CompletedTaskItem(task = task)
                        }
                    }
                }
            }
        }

        if (showAddDialog || taskToEdit != null) {
            TaskDialog(
                onDismiss = {
                    showAddDialog = false
                    taskToEdit = null
                },
                onConfirm = { title, description, dueDate ->
                    if (taskToEdit != null) {
                        viewModel.updateTask(taskToEdit!!.copy(
                            title = title,
                            description = description,
                            dueDate = dueDate
                        ))
                    } else {
                        viewModel.addTask(title, description, dueDate)
                    }
                },
                task = taskToEdit
            )
        }
    }
}