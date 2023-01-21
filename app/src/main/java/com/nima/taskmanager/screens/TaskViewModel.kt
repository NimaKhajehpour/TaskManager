package com.nima.taskmanager.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.taskmanager.data.Note
import com.nima.taskmanager.data.Task
import com.nima.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository): ViewModel() {

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())

    val taskList = _taskList.asStateFlow()
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllTasks().distinctUntilChanged().collect(){
                _taskList.value = it
            }
        }

        viewModelScope.launch (Dispatchers.IO){
            repository.getNotes().distinctUntilChanged().collect(){
                _noteList.value = it
            }
        }
    }

     fun getTask(id: String): Task = runBlocking {
         repository.getTask(id)
     }

    fun addTask(task: Task) = viewModelScope.launch{ repository.addTask(task)}
    fun addNote(note: Note) = viewModelScope.launch{ repository.addNote(note)}
    fun updateTask(task: Task) = viewModelScope.launch{ repository.updateTask(task)}
    fun deleteAllTasks() = viewModelScope.launch{ repository.deleteAllTasks()}
    fun deleteAllNotes(id: String) = viewModelScope.launch{ repository.deleteAllNotes(id)}
    fun deleteTask(task: Task) = viewModelScope.launch{ repository.deleteTask(task)}
    fun deleteNote(note: Note) = viewModelScope.launch{ repository.deleteNote(note)}
}