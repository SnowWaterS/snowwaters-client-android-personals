package com.har.habittracker.presentation.addition

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.har.habittracker.domain.model.HabitTask
import com.har.habittracker.domain.usecase.AddHabitTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewTaskDialogViewModel @Inject constructor(
        private val addHabitTaskUseCase: AddHabitTaskUseCase
) : ViewModel() {

    private val _title = MutableLiveData("")
    private val _description = MutableLiveData("")
    private val _date = MutableLiveData(Calendar.getInstance().timeInMillis)

    val title: LiveData<String>
        get() = _title

    val description: LiveData<String>
        get() = _description

    val date: LiveData<Long>
        get() = _date

    fun onAddButtonClicked(view: View) {
        val titleValue = title.value ?: ""
        val descriptionValue = description.value ?: ""
        val dateLongValue = date.value ?: 0L

        if (titleValue.isNotEmpty()) {
            viewModelScope.launch (Dispatchers.IO) {
                val newHabitTask = HabitTask(titleValue, descriptionValue, dateLongValue)
                addHabitTaskUseCase.execute(newHabitTask)
            }
        }
    }
}