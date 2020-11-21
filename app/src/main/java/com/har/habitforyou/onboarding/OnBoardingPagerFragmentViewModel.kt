package com.har.habitforyou.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingPagerFragmentViewModel: ViewModel() {

    private val _position: MutableLiveData<String> = MutableLiveData()

    val position
        get() = _position

    init {
        _position.value = ""
    }

    fun setPosition(position: Int) {
        val testText = "Show from Pager #$position+1"
        _position.postValue(testText)
    }
}