package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothClass
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

    val _noDeviceTextViewVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val _pairedListVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val _scannedListVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _pairedList.value = listOf()
        _scannedList.value = listOf()
        _noDeviceTextViewVisibility.value = false
        _pairedListVisibility.value = false
        _scannedListVisibility.value = false
    }

    val pairedList: LiveData<List<BluetoothDevice>>
        get() = _pairedList

    fun setPairedList(pairedList: List<BluetoothDevice>) {
        _pairedList.postValue(pairedList)
    }

    val scannedList: LiveData<List<BluetoothDevice>>
        get() = _scannedList

    fun setScannedList(scannedList: List<BluetoothDevice>) {
        _scannedList.postValue(scannedList)
    }

    val noDeviceTextViewVisibility: LiveData<Boolean>
        get() = _noDeviceTextViewVisibility

    fun setNoDeviceTextViewVisibility(isVisible: Boolean) {
        _noDeviceTextViewVisibility.postValue(isVisible)
    }

    val pairedListVisibility: LiveData<Boolean>
        get() = _pairedListVisibility

    fun setPairedListVisibilty(isVisible: Boolean) {
        _pairedListVisibility.postValue(isVisible)
    }

    val scannedListVisibility: LiveData<Boolean>
        get() = _scannedListVisibility

    fun setScannedListVisibilty(isVisible: Boolean) {
        _scannedListVisibility.postValue(isVisible)
    }

    fun setScannedDeviceList() {
        BluetoothUtil.instance?.getScannedDevices(object: ResultCallback<Set<BluetoothDevice>> {
            override fun onResult(result: Result<Set<BluetoothDevice>>) {
                hideLoading()
                if (result.code != -1) {
                    val pairedSet = BluetoothUtil.instance?.getPairedDevices()?.toList()
                    val scannedSet = result.data?.toList()
                    setPairedList(pairedSet ?: listOf())
                    setScannedList(scannedSet?.filter { pairedSet?.contains(it) == false } ?: listOf())
                } else {
                    setPairedList(listOf())
                    setScannedList(listOf())
                    setNoDeviceTextViewVisibility(true)
                }
            }
        })
    }

    fun isDeivcePrinter(type: Int): Boolean {
        return type == BluetoothClass.Device.Major.IMAGING
    }

    fun connectToBluetoothDevice(bluetoothDevice: BluetoothDevice) {
        val pairedList = _pairedList.value ?: listOf()
        if (pairedList.contains(bluetoothDevice)) {
            BluetoothUtil.instance?.connectBluetoothDevices(bluetoothDevice.address)
        } else {
            BluetoothUtil.instance?.bindBluetoothDevices(bluetoothDevice)
        }

    }

}