package com.nima.taskmanager.data

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter{
    @TypeConverter
    fun fromUUID(uuid: UUID) = uuid.toString()

    @TypeConverter
    fun toUUID(id: String) = UUID.fromString(id)
}