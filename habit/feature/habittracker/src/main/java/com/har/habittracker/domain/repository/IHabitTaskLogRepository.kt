package com.har.habittracker.domain.repository

import androidx.lifecycle.LiveData
import com.har.habittracker.domain.model.HabitTaskLog

interface IHabitTaskLogRepository {

    fun getHabitTaskLogById(habitTaskId: Long): LiveData<List<HabitTaskLog>>

    fun checkHabitTaskLog(habitTaskId: Long)

    fun cancelHabitTaskLog(habitTaskId: Long)

}