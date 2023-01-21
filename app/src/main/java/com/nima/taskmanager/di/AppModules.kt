package com.nima.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.nima.taskmanager.data.TaskDao
import com.nima.taskmanager.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase
        = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "TaskDatabase"
        ).fallbackToDestructiveMigration().build()
}