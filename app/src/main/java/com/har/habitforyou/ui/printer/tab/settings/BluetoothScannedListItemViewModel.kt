package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel

class BluetoothScannedListItemViewModel(private val bluetoothDevice: BluetoothDevice): ViewModel() {

    val name: String
            get() = bluetoothDevice.name ?: "<<알 수 없음>>"

    val address: String
        get() = bluetoothDevice.address ?: "--:--:--:--:--:--"


    // 프린터 -> Imaging
    // TV -> Video / Audio
    // 폰/태블릿 -> Phone
    val type:String
        get() {
            return when(bluetoothDevice.bluetoothClass.majorDeviceClass) {
                BluetoothClass.Device.Major.IMAGING -> "Printer"
                BluetoothClass.Device.Major.AUDIO_VIDEO -> "Audio/Video"
                BluetoothClass.Device.Major.PERIPHERAL -> "Peripheral"
                BluetoothClass.Device.Major.PHONE -> "Phone/Tablet"
                BluetoothClass.Device.Major.COMPUTER -> "Computer"
                else -> "Devices"
            }
        }
}