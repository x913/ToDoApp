package com.k3kc.todoapp20.data.repository

import androidx.lifecycle.LiveData
import com.k3kc.todoapp20.data.ToDoDao
import com.k3kc.todoapp20.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

}