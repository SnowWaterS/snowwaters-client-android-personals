package com.har.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class GetHabitTaskUseCase(private val repository: IHabitTaskRepository) {
    fun execute(): LiveData<List<HabitTask>> {
        return repository.getHabitTask()
    }

    fun execute(taskId: Int): LiveData<HabitTask> {
        return repository.getHabitTask(taskId)
    }
}