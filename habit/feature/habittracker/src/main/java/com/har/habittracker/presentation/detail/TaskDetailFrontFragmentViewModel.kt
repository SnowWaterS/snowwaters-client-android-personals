package com.har.habittracker.presentation.detail

import androidx.lifecycle.*
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.usecase.GetHabitTaskUseCase
import javax.inject.Inject

class TaskDetailFrontFragmentViewModel @Inject constructor(
        val getHabitTaskUseCase: GetHabitTaskUseCase
) : ViewModel() {

    private var _taskId = MutableLiveData(0)

    val habitTask: LiveData<HabitTask> = _taskId.switchMap {
        getHabitTaskUseCase.execute(it)
    }

    fun fetch(taskId: Int) {
        if (taskId > 0) {
            _taskId.postValue(taskId)
        }
    }
}