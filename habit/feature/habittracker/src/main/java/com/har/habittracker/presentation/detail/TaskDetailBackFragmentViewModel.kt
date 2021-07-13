package com.har.habittracker.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.usecase.GetHabitTaskUseCase
import javax.inject.Inject

class TaskDetailBackFragmentViewModel @Inject constructor(
    val getHabitTaskUseCase: GetHabitTaskUseCase
) : ViewModel() {

    private val _taskId = MutableLiveData<Int> (0)

    val habitTask: LiveData<HabitTask> = _taskId.switchMap {
        getHabitTaskUseCase.execute(it)
    }

    fun fetch (taskId: Int) {
        if (taskId > 0) {
            _taskId.postValue(taskId)
        }
    }
}