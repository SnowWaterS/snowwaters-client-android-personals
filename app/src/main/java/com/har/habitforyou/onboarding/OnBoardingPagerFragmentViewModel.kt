package com.har.habitforyou.onboarding

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingPagerFragmentViewModel: ViewModel() {

    private val _position: MutableLiveData<String> = MutableLiveData()

    private val _dismissEvent: MutableLiveData<Boolean> = MutableLiveData()

    private val _btnStartVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val position
        get() = _position

    val btnStartVisibility
        get() = _btnStartVisibility

    init {
        _position.value = ""

        _dismissEvent.value = false

        _btnStartVisibility.value = false
    }

    fun setPosition(position: Int) {
        val testText = "Show from Pager #$position+1"
        _position.postValue(testText)
    }

    fun onClickBtnStart(view: View) {
        onDismiss()
    }

    fun onDismiss() {
        // set listener and close the dialog
    }
}