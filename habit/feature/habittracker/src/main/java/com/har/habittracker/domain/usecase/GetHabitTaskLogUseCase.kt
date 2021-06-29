package com.har.habittracker.domain.usecase

import com.har.habittracker.domain.repository.IHabitTaskLogRepository

class GetHabitTaskLogUseCase (private val repository: IHabitTaskLogRepository) {
    suspend fun execute(habitTaskId: Long) {
        repository.getHabitTaskLog(habitTaskId)
    }
}