package com.ms.taskmanagermobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(
    currentRoute: String,
    onRouteChange: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Tâches actives") },
            label = { Text("À faire") },
            selected = currentRoute == "tasks",
            onClick = { onRouteChange("tasks") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Tâches terminées") },
            label = { Text("Terminées") },
            selected = currentRoute == "completed",
            onClick = { onRouteChange("completed") }
        )
    }
} 