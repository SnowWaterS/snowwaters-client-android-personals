package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.har.habitforyou.base.BaseBindingDialogViewModel

class BluetoothConnectionDialogViewModel : BaseBindingDialogViewModel() {

    val _scannedList: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()

    val _scannedListVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _scannedList.value = listOf()
        _scannedListVisibility.value = false
    }

    val scannedList: LiveData<List<BluetoothDevice>>
        get() = _scannedList

    val scannedListVisibility: LiveData<Boolean>
        get() = _scannedListVisibility

    fun setScannedListVisibilty(isVisible: Boolean) {
        _scannedListVisibility.postValue(isVisible)
    }

}