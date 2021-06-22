package com.har.habittracker.data.repository

import androidx.lifecycle.LiveData
import com.har.habittracker.data.datasource.TaskMemoryDataSource
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class TaskRepository(
    private val taskMemoryDataSource: TaskMemoryDataSource
): IHabitTaskRepository {

    override fun getHabitTask(): LiveData<List<HabitTask>> {
        TODO("Not yet implemented")
    }

    override fun addHabitTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

    override fun updateHabitTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

    override fun deleteHabitTask(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteHabitTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

}