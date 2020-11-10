package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel

class BluetoothScannedListItemViewModel(private val bluetoothDevice: BluetoothDevice): ViewModel() {

    val name: String
            get() = bluetoothDevice.name ?: "<<알 수 없음>>"

    val address: String
        get() = bluetoothDevice.address ?: "--:--:--:--:--:--"
}