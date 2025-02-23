package com.ms.taskmanagermobile.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDateTime) -> Unit,
    initialDate: LocalDate
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            .atStartOfDay()
            .atZone(java.time.ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val date = java.time.Instant.ofEpochMilli(millis)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime()
                    onDateSelected(date)
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Annuler")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    initialTime: LocalDateTime
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(timePickerState.hour, timePickerState.minute)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Annuler")
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
} 