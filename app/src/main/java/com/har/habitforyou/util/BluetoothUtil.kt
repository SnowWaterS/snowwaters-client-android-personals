package com.har.habitforyou.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.widget.Toast

class BluetoothUtil {

    companion object {
        @get:Synchronized
        var instance: BluetoothUtil? = null
            get() = field ?: BluetoothUtil()
    }

    fun getScannedBluetoothDevices(): List<Pair<String, String>>  {
        val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter == null) {
            Toast.makeText(ContextUtil.appContext, "블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show()
            return emptyList()
        }

        if (!btAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ContextUtil.appContext?.startActivity(enableBtIntent)
        }

        val pairedDevices: Set<BluetoothDevice>? = btAdapter.bondedDevices
        return pairedDevices?.map { Pair(it.address, it.name) } ?: listOf()
    }

    fun connectBluetoothDevices() {

    }

    fun disconnectBluetoothDevices() {

    }
}