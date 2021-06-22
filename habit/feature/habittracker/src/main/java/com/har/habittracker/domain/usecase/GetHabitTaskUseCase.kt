package com.har.habittracker.domain.usecase

import androidx.lifecycle.LiveData
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.repository.IHabitTaskRepository

class GetHabitTaskUseCase(private val repository: IHabitTaskRepository) {
    suspend fun execute(): LiveData<List<HabitTask>> {
        return repository.getHabitTask()
    }
}