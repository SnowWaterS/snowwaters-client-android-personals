package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice

interface BluetoothSelectListener {
    fun onSelect(position: Int, bluetoothDevice: BluetoothDevice)
}