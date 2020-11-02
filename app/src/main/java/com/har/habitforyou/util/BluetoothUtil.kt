package com.har.habitforyou.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast

class BluetoothUtil {

    companion object {

        private const val TAG = "BluetoothUtil"

        @get:Synchronized
        var instance: BluetoothUtil? = null
            get() = field ?: BluetoothUtil()
    }

    private val scannedBluetoothSet: MutableSet<Pair<String, String>> = mutableSetOf()

    private val bluetoothScanBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.d(TAG, "블루투스 기기 발견!")
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name ?: ""
                    val deviceHardwareAddress = device?.address ?: ""
                    scannedBluetoothSet.add(Pair(deviceName, deviceHardwareAddress))
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d(TAG, "블루투스 스캔 시작")
                    scannedBluetoothSet.clear()
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d(TAG, "블루투스 스캔 완료")
                     unregisterBroadcastReceiver()
                }
            }
        }
    }

    fun scannedBluetoothDevices() {
        val context = ContextUtil.appContext?.applicationContext
        val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter == null) {
            Toast.makeText(context, "블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show()
//            return emptyList()
            return
        }

        if (!btAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            Toast.makeText(context, "블루투스를 활성화 시켜주세요.", Toast.LENGTH_LONG).show()

//            context?.startActivity(enableBtIntent)
//            return emptyList()
            return
        }

        // 등록된 기기 리스트
        val pairedDevices: Set<BluetoothDevice>? = btAdapter.bondedDevices
        pairedDevices?.forEach {
            Log.d(TAG, "등록된 기기: ${it.name} + ${it.address}")
        }


        registerBroadcastReceiver()
        btAdapter.startDiscovery()

    }

    fun connectBluetoothDevices() {

    }

    fun disconnectBluetoothDevices() {

    }

    private fun registerBroadcastReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        val context = ContextUtil.appContext?.applicationContext
        context?.registerReceiver(bluetoothScanBroadcastReceiver, filter)
    }

    private fun unregisterBroadcastReceiver() {
        scannedBluetoothSet.forEach {
            Log.d(TAG, "기기: ${it.first} + ${it.second}")
        }
        val context = ContextUtil.appContext?.applicationContext
        context?.unregisterReceiver(bluetoothScanBroadcastReceiver)
    }

}