package com.example.my_app.model

// Data class that represents a single task in the application
data class Task(
    val id: Long,          // Unique ID for each task
    val title: String,     // Task title entered by the user
    val priority: Priority, // Priority level of the task
    var isDone: Boolean = false // Indicates whether the task is completed
)

