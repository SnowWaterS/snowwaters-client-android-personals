package com.har.habitforyou.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseBindingDialogViewModel: ViewModel() {

    val _dismissEvent = MutableLiveData<Boolean>()

    init {
        _dismissEvent.value = false
    }

    fun onDismissButtonClick(view: View) {
        onDismiss()
    }

    fun setDismissEvent(isDismiss: Boolean) {
        _dismissEvent.postValue(true)
    }

    fun getDismissEvent(): LiveData<Boolean> {
        return _dismissEvent
    }

    protected open fun onDismiss() {
        setDismissEvent(true)
    }
}