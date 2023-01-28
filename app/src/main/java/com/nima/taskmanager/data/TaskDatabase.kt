package com.nima.taskmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(UUIDConverter::class)
@Database(entities = [Task::class, Note::class], version = 2, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}