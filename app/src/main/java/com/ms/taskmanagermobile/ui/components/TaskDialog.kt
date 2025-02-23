package com.ms.taskmanagermobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ms.taskmanagermobile.data.model.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Gestion de toute interaction utilisateur pour la création et la modification des tâches
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, LocalDateTime) -> Unit,
    task: Task? = null
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var selectedDate by remember { mutableStateOf(task?.dueDate ?: LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "Ajouter une tâche" else "Modifier la tâche") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Titre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Date: ${selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}")
                }

                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Heure: ${selectedDate.format(DateTimeFormatter.ofPattern("HH:mm"))}")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title, description, selectedDate)
                        onDismiss()
                    }
                }
            ) {
                Text(if (task == null) "Ajouter" else "Modifier")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                selectedDate = selectedDate.withYear(date.year)
                    .withMonth(date.monthValue)
                    .withDayOfMonth(date.dayOfMonth)
                showDatePicker = false
            },
            initialDate = selectedDate.toLocalDate()
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onTimeSelected = { hour, minute ->
                selectedDate = selectedDate.withHour(hour).withMinute(minute)
                showTimePicker = false
            },
            initialTime = selectedDate
        )
    }
} 