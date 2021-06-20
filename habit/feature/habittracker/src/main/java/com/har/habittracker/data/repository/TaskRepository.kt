package com.har.habittracker.data.repository

import com.har.habittracker.data.datasource.TaskMemoryDataSource
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class TaskRepository(
    private val taskMemoryDataSource: TaskMemoryDataSource
): IHabitTaskRepository {

    override fun getTask(): HabitTask {
        TODO("Not yet implemented")
    }

    override fun addTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(task: HabitTask) {
        TODO("Not yet implemented")
    }

}