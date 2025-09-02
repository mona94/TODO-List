package com.example.todo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo.data.db.TodoDAO
import com.example.todo.data.entity.TodoTask

open class Repository(private val dao: TodoDAO) {

//    code used before room db
//    private val tasks = mutableListOf<TodoTask>()
//    private val _allTasks = MutableLiveData<List<TodoTask>>()
//    val allTasks : LiveData<List<TodoTask>> = _allTasks
//
//    fun addTask(task : TodoTask){
//        tasks.add(task)
//        _allTasks.value = tasks.toList()
//    }
//
//    fun deleteTask(task : TodoTask){
//        tasks.remove(task)
//        _allTasks.value = tasks.toList()
//    }
//
//    fun updateTask(task : TodoTask){
//        val index : Int = tasks.indexOfFirst { it.id ==task.id }
//        if(index != -1){
//            tasks[index] = task
//            _allTasks.value = tasks.toList()
//        }
//    }


    //    Code Using RoomDB
    suspend fun insertTask(task: TodoTask): Long = dao.addTodoTask(task)
    suspend fun updateTask(task: TodoTask) = dao.updateTask(task)

    suspend fun deleteTask(task: TodoTask) = dao.deleteTodoTask(task)

    fun getAllTasks(): kotlinx.coroutines.flow.Flow<List<TodoTask>> = dao.getAllTodoTask()

}