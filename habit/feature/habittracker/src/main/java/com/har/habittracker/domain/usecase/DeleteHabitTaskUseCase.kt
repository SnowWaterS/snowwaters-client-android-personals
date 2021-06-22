package com.har.habittracker.domain.usecase

import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class DeleteHabitTaskUseCase(private val repository: IHabitTaskRepository) {

    suspend fun execute(id: Long) {
        repository.deleteHabitTask(id)
    }

    suspend fun execute(task: HabitTask) {
        repository.deleteHabitTask(task)
    }
}