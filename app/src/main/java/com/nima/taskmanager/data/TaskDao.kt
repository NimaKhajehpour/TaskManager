package com.nima.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select  * from task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("select * from note")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from task where id=:id")
    suspend fun getTask(id: String): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("delete from task")
    suspend fun deleteAllTasks()

    @Query("delete from note where foreignKey=:id")
    suspend fun deleteAllNote(id: String)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteTask(task: Task)

}