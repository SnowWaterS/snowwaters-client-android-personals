package com.har.habitforyou.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.har.habitforyou.R

open class BaseBindingDialogViewModel: ViewModel() {

    private val _loadingData = MutableLiveData<LoadingData?>()

    private val _dismissEvent = MutableLiveData<Boolean>()

    init {
        _loadingData.value = LoadingData(R.string.empty_text, false)
        _dismissEvent.value = false
    }

    fun onDismissButtonClick(view: View) {
        onDismiss()
    }

    fun getLoadingData(): LiveData<LoadingData?> {
        return _loadingData
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

    open fun showLoading(resId: Int = R.string.base_binding_dialog_default_loading_text) {
        val loadingData = _loadingData.value ?: LoadingData(resId, true)
        loadingData.messageResId = resId
        loadingData.isLoading = true

        _loadingData.postValue(loadingData)

    }

    open fun hideLoading() {
        val loadingData = _loadingData.value
        loadingData?.apply {
            isLoading = false
        }
        _loadingData.postValue(loadingData)
    }
}