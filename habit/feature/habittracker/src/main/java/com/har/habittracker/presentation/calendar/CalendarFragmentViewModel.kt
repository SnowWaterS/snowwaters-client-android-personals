package com.har.habittracker.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.usecase.GetHabitTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarFragmentViewModel @Inject constructor(
    private val getHabitTaskUseCase: GetHabitTaskUseCase
): ViewModel() {

    val habitTaskList: LiveData<List<HabitTask>>
        get() = getHabitTaskUseCase.execute()

}