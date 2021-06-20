package com.har.habittracker.domain.repository

import com.google.android.play.core.tasks.Task
import com.har.habittracker.domain.model.HabitTask

interface IHabitTaskRepository {

    fun getTask(): HabitTask

    fun addTask(task: HabitTask)

    fun updateTask(task: HabitTask)

    fun deleteTask(id: Long)
    fun deleteTask(task: HabitTask)
}