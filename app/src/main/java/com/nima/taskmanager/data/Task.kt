package com.nima.taskmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var description: String,
    @ColumnInfo
    var done: Boolean = false,
    @ColumnInfo
    var date: String,
    @ColumnInfo
    var time: String
)