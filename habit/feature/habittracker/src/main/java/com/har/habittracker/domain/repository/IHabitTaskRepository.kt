package com.har.habittracker.domain.repository

import androidx.lifecycle.LiveData
import com.har.habittracker.domain.model.HabitTask

interface IHabitTaskRepository {

    fun getHabitTask(): LiveData<List<HabitTask>>
    fun getHabitTask(id: Int): LiveData<HabitTask>


    fun addHabitTask(task: HabitTask)

    fun updateHabitTask(task: HabitTask)

    fun deleteHabitTask(id: Int)
    fun deleteHabitTask(task: HabitTask)
}