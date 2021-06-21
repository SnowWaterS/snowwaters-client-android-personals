package com.har.habittracker.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.har.habittracker.domain.model.HabitTask

class CalendarFragmentViewModel: ViewModel() {

    private val _taskList = MutableLiveData<List<HabitTask>>(null)

    val taskList: LiveData<List<HabitTask>>
        get() = _taskList
}