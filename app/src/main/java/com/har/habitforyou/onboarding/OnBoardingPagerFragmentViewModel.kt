package com.har.habitforyou.onboarding

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingPagerFragmentViewModel: ViewModel() {

    private val _position: MutableLiveData<String> = MutableLiveData()
    private val _btnStartVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val _dismissEvent: MutableLiveData<Boolean> = MutableLiveData()

    val position
        get() = _position

    val btnStartVisibility
        get() = _btnStartVisibility

    val dismissEvent
        get() = _dismissEvent

    init {
        _position.value = ""

        _dismissEvent.value = false

        _btnStartVisibility.value = false
    }

    fun setPosition(position: Int) {
        val testText = "Show from Pager #${position+1}"
        _position.postValue(testText)

        if (position == 3) {
            _btnStartVisibility.postValue(true)
        }
    }

    fun onClickBtnStart(view: View) {
        onDismiss()
    }

    private fun onDismiss() {
        _dismissEvent.postValue(true)
    }
}