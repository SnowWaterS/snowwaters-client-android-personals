package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.har.habitforyou.`object`.Result
import com.har.habitforyou.`object`.ResultCallback
import com.har.habitforyou.base.BaseBindingDialogViewModel
import com.har.habitforyou.util.BluetoothUtil

class BluetoothConnectionDialogViewModel : BaseBindingDialogViewModel() {

    val _pairedList: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()
    val _scannedList: MutableLiveData<List<BluetoothDevice>> = MutableLiveData()

    val _scannedListVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _pairedList.value = listOf()
        _scannedList.value = listOf()
        _scannedListVisibility.value = false
    }

    val pairedList: LiveData<List<BluetoothDevice>>
        get() = _pairedList

    fun setPaireddList(pairedList: List<BluetoothDevice>) {
        _pairedList.postValue(pairedList)
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
        BluetoothUtil.instance?.getScannedDevices(object: ResultCallback<Set<BluetoothDevice>> {
            override fun onResult(result: Result<Set<BluetoothDevice>>) {
                if (result.code != -1) {
                    val pairedSet = BluetoothUtil.instance?.getPairedDevices()?.toList()
                    val scannedSet = result.data?.toList()
                    setPaireddList(pairedSet ?: listOf())
                    setScannedList(scannedSet?.filter { pairedSet?.contains(it) == false } ?: listOf())
                }
            }
        })
    }

}