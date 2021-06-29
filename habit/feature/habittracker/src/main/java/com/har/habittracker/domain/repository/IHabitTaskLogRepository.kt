package com.har.habittracker.domain.repository

import androidx.lifecycle.LiveData
import com.har.habittracker.domain.model.HabitTaskLog

interface IHabitTaskLogRepository {

    fun getHabitTaskLog(habitTaskId: Long): LiveData<List<HabitTaskLog>>

    fun checkHabitTaskDone(habitTaskId: Long)

    fun cancelHabitTaskLog(habitTaskId: Long)

}