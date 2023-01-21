package com.nima.taskmanager.repository

import com.nima.taskmanager.data.Note
import com.nima.taskmanager.data.Task
import com.nima.taskmanager.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks().flowOn(Dispatchers.IO).conflate()
    fun getNotes(): Flow<List<Note>> = taskDao.getNotes().flowOn(Dispatchers.IO).conflate()
    suspend fun getTask(id: String): Task = taskDao.getTask(id)
    suspend fun addTask(task: Task) = taskDao.addTask(task)
    suspend fun addNote(note: Note) = taskDao.addNote(note)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()
    suspend fun deleteAllNotes(id: String) = taskDao.deleteAllNote(id)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun deleteNote(note: Note) = taskDao.deleteNote(note)
}