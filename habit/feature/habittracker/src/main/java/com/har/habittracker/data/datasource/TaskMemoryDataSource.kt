package com.har.habittracker.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.tasks.Task
import com.har.habittracker.domain.model.HabitTask

class TaskMemoryDataSource {

    private val _habitTask = MutableLiveData<HabitTask>(null)

    val habitTask: LiveData<HabitTask>
        get() = _habitTask

    fun addHabitTask(task: HabitTask) {

    }
}