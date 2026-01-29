package com.example.my_app.model

data class Task(
    val id: Long,
    val title: String,
    val priority: Priority,
    var isDone: Boolean = false
)
