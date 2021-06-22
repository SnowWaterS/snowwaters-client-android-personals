package com.har.habittracker.domain.usecase

import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class AddHabitTaskUseCase(private val repository: IHabitTaskRepository) {

    suspend fun execute(habitTask: HabitTask) {
        repository.addHabitTask(habitTask)
    }
}