package com.example.todo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.data.entity.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Query("Select * from TodoTask")
    fun getAllTodoTask(): Flow<List<TodoTask>>

    @Insert
    suspend fun addTodoTask(todoTask: TodoTask): Long

    @Delete
    suspend fun deleteTodoTask(todoTask: TodoTask)

    @Update
    suspend fun updateTask(task: TodoTask)
}