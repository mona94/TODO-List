package com.example.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.data.entity.TodoTask

@Database(entities = [TodoTask::class], version = 1)
abstract class TodoDatabase  : RoomDatabase() {
    companion object{
        const val NAME = "Todo_DB"
    }

    abstract fun getTodoDao() : TodoDAO

}