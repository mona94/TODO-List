package com.example.todo.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo.data.entity.TodoTask
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repository.Repository
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: Repository) : ViewModel() {


    val tasks: LiveData<List<TodoTask>> = repo.getAllTasks().asLiveData()

    fun addTask(task: TodoTask) {
        viewModelScope.launch {
            repo.insertTask(task)
        }
    }

    fun updateTask(task: TodoTask) {
        viewModelScope.launch {
//see if this work
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            repo.updateTask(updatedTask)}
    }

    fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            repo.deleteTask(task)
        }
    }


//
//
//    fun addTask(title: String, description: String) {
//        val task = TodoTask(taskCounterId++,title,description,)
//        repository.addTask(task)
//    }
//    fun deleteTask(task : TodoTask){
//        repository.deleteTask(task)
//    }
//    fun toggleTaskCompleteion(task:TodoTask){
//        repository.updateTask( task.copy(isCompleted = !(task.isCompleted)))
//    }
}