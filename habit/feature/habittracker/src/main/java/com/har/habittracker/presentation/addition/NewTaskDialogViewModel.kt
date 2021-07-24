package com.har.habittracker.presentation.addition

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.usecase.AddHabitTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewTaskDialogViewModel @Inject constructor(
        private val addHabitTaskUseCase: AddHabitTaskUseCase
) : ViewModel() {



    var title = MutableLiveData("")
    var description = MutableLiveData("")

    var hourOfDay = MutableLiveData(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    var minute = MutableLiveData(Calendar.getInstance().get(Calendar.MINUTE))

    fun onTimeChanged(view: View, hourOfDay: Int, minute: Int) {
        this.hourOfDay.postValue(hourOfDay)
        this.minute.postValue(minute)
    }

    fun onAddButtonClicked(view: View) {
        val titleValue = title.value ?: ""
        val descriptionValue = description.value ?: ""
        val dateLongValue = Calendar.getInstance().timeInMillis

        if (titleValue.isNotEmpty()) {
            viewModelScope.launch (Dispatchers.IO) {
                val newHabitTask = HabitTask(titleValue, descriptionValue, dateLongValue)
                addHabitTaskUseCase.execute(newHabitTask)
            }
        }
    }
}