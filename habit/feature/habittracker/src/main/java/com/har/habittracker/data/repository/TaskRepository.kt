package com.har.habittracker.data.repository

import androidx.lifecycle.LiveData
import com.har.habittracker.data.dao.HabitTaskDao
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class TaskRepository(
    private val dao: HabitTaskDao
): IHabitTaskRepository {

    override fun getHabitTask(): LiveData<List<HabitTask>> {
        return dao.getLiveAllDataHabitTask()
    }

    override fun getHabitTask(taskId: Int): LiveData<HabitTask> {
        return dao.getLiveDataHabitTaskById(taskId)
    }

    override fun addHabitTask(task: HabitTask) {
        dao.insertHabitTask(task)
    }

    override fun updateHabitTask(task: HabitTask) {
        dao.updateHabitTask(task)
    }

    override fun deleteHabitTask(taskId: Int) {
        dao.deleteHabitTaskById(taskId)
    }

    override fun deleteHabitTask(task: HabitTask) {
        dao.deleteHabitTask(task)
    }

}