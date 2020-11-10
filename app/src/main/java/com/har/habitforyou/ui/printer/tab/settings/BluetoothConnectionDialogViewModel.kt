package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.har.habitforyou.`object`.Result
import com.har.habitforyou.`object`.ResultCallback
import com.har.habitforyou.base.BaseBindingDialogViewModel
import com.har.habitforyou.util.BluetoothUtil

class BluetoothConnectionDialogViewModel : BaseBindingDialogViewModel() {

    val _scannedList: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()

    val _scannedListVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _scannedList.value = listOf()
        _scannedListVisibility.value = false
    }

    val scannedList: LiveData<List<BluetoothDevice>>
        get() = _scannedList

    fun setScannedList(scannedList: List<BluetoothDevice>) {
        _scannedList.postValue(scannedList)
    }

    val scannedListVisibility: LiveData<Boolean>
        get() = _scannedListVisibility

    fun setScannedListVisibilty(isVisible: Boolean) {
        _scannedListVisibility.postValue(isVisible)
    }

    fun setScannedDeviceList() {
        BluetoothUtil.instance?.getScannedBluetoothDevices(object: ResultCallback<Set<BluetoothDevice>> {
            override fun onResult(result: Result<Set<BluetoothDevice>>) {
                if (result.code != -1) {
                    setScannedList(result.data?.toList() ?: listOf())
                } else {
                    Log.d("Bluetooth", result.message)
                }
            }
        })
    }

}