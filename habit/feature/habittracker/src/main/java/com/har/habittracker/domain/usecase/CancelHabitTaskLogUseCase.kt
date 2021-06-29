package com.har.habittracker.domain.usecase

import com.har.habittracker.domain.repository.IHabitTaskLogRepository

class CancelHabitTaskLogUseCase(private val repository: IHabitTaskLogRepository) {
    suspend fun execute(habitTaskId: Long) {
        repository.cancelHabitTaskLog(habitTaskId)
    }
}