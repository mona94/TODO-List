package com.example.todo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TodoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val des: String,
    val createdAt: String,
    var isCompleted: Boolean = false
)
