package com.har.habitforyou.service

import android.app.Service
import android.bluetooth.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BluetoothBleService : Service() {

    private val TAG = "BluetoothBleService"

    private val _binder: IBinder = LocalBinder()

    private var _bluetoothManager: BluetoothManager? = null
    private var _bluetoothAdapter: BluetoothAdapter? = null

    private var _bluetoothDeviceAddress: String = ""
    private var _bluetoothGatt: BluetoothGatt? = null

    private val _gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(
            gatt: BluetoothGatt,
            status: Int,
            newState: Int
        ) {
            val intentAction: String
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "Connected to GATT server.")
                Log.i(TAG, "Attempting to start service discovery:" + _bluetoothGatt?.discoverServices())
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "Disconnected from GATT server.")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "_bluetoothGatt = $_bluetoothGatt")
            } else {
                Log.w(TAG, "onServicesDiscovered received: $status")
            }
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "onCharacteristicRead $characteristic")
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            Log.i(TAG, "onCharacteristicChanged $characteristic")
        }
    }


    inner class LocalBinder : Binder() {
        val service: BluetoothBleService
            get() = this@BluetoothBleService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return _binder
    }

    fun initialize(): Boolean {
        _bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return _bluetoothAdapter != null
    }

    fun connect(address: String): Boolean {
        if (_bluetoothAdapter == null) {
            return false
        }

        if (_bluetoothDeviceAddress.isNotEmpty()
            && address == _bluetoothDeviceAddress && _bluetoothGatt != null) {
            return _bluetoothGatt?.connect() ?: false
        }

        val device = _bluetoothAdapter?.getRemoteDevice(address) ?: return false
        _bluetoothGatt = device.connectGatt(this, false, _gattCallback)
        _bluetoothDeviceAddress = address
        return true
    }
}
