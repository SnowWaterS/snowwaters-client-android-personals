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

    private val _pairedList: MutableLiveData<Pair<List<BluetoothDevice>, Boolean>> = MutableLiveData()
    private val _scannedList: MutableLiveData<Pair<List<BluetoothDevice>, Boolean>> = MutableLiveData()

    private val _noDeviceTextViewVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val _pairedListVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val _scannedListVisibility: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _pairedList.value = Pair(listOf(), false)
        _scannedList.value = Pair(listOf(), false)
        _noDeviceTextViewVisibility.value = false
        _pairedListVisibility.value = false
        _scannedListVisibility.value = false
    }

    val pairedList: LiveData<Pair<List<BluetoothDevice>, Boolean>>
        get() = _pairedList

    fun setPairedList(pairedList: Pair<List<BluetoothDevice>, Boolean>) {
        _pairedList.postValue(pairedList)
    }

    val scannedList: LiveData<Pair<List<BluetoothDevice>, Boolean>>
        get() = _scannedList

    fun setScannedList(scannedList: Pair<List<BluetoothDevice>, Boolean>) {
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
                    setPairedList(Pair(pairedSet ?: listOf(), true))
                    setScannedList(Pair(scannedSet?.filter { pairedSet?.contains(it) == false } ?: listOf(), false))
                } else {
                    setPairedList(Pair(listOf(), true))
                    setScannedList(Pair(listOf(), true))
                    setNoDeviceTextViewVisibility(true)
                }
            }
        })
    }

    fun isDeivcePrinter(type: Int): Boolean {
        return type == BluetoothClass.Device.Major.IMAGING
    }

    fun connectToBluetoothDevice(bluetoothDevice: BluetoothDevice) {
        val pairedList = _pairedList.value?.first ?: listOf()
        if (pairedList.contains(bluetoothDevice)) {
            BluetoothUtil.instance?.connectBluetoothDevices(bluetoothDevice.address)
        } else {
            BluetoothUtil.instance?.bindBluetoothDevices(bluetoothDevice)
        }

    }

}