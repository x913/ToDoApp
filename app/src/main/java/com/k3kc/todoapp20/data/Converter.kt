package com.k3kc.todoapp20.data

import androidx.room.TypeConverter
import com.k3kc.todoapp20.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}