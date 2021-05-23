package com.har.habitforyou.service

import android.app.Service
import android.bluetooth.*
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*

class BluetoothBleService: Service() {

    private val TAG = "BluetoothBleService"

    private val _binder: IBinder = LocalBinder()

    private var _bluetoothManager: BluetoothManager? = null
    private var _bluetoothAdapter: BluetoothAdapter? = null

    private var _bluetoothDeviceAddress: String = ""
    private var _bluetoothGatt: BluetoothGatt? = null

    private var _sendByteCounter = 0
    private var _sendData: ByteArray? = null

    private val TX_POWER_LEVEL_UUID = UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb")
    private val FIRMWARE_REVISON_UUID = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb")

    /*  Client Characteristic Configuration Descriptor */
    private val CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

    private val TX_POWER_UUID = UUID.fromString("00001804-0000-1000-8000-00805f9b34fb")
    private val DEVICE_INFO_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb")

    private val RX_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e")
    private val RX_CHAR_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e")
    private val TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e")

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

        override fun onCharacteristicWrite(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "onCharacteristicRead $characteristic")
            }
            send()
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

    fun disconnect() {
        if (_bluetoothAdapter == null || _bluetoothGatt == null) {
            return
        }

        _bluetoothGatt?.disconnect()
        _bluetoothGatt?.close()
        _bluetoothGatt = null
    }

    fun deinitialize() {
        _bluetoothDeviceAddress = ""

        _bluetoothGatt?.close()
        _bluetoothGatt = null
        _bluetoothAdapter = null
    }

    fun enableWrite() {
        val rxService = _bluetoothGatt?.getService(RX_SERVICE_UUID) ?: return
        val txChar = rxService.getCharacteristic(TX_CHAR_UUID) ?: return

        _bluetoothGatt?.setCharacteristicNotification(txChar, true)

        val descriptor = txChar.getDescriptor(CCCD)
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        _bluetoothGatt?.writeDescriptor(descriptor)
    }

    fun disableWrite() {
        val rxService = _bluetoothGatt?.getService(RX_SERVICE_UUID) ?: return
        val txChar = rxService.getCharacteristic(TX_CHAR_UUID) ?: return

        _bluetoothGatt?.setCharacteristicNotification(txChar, false)

        val descriptor = txChar.getDescriptor(CCCD)
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        _bluetoothGatt?.writeDescriptor(descriptor)
    }

    fun sendData(data: ByteArray?) {
        _sendData = data
        send()
    }

    private fun send() {
        if (_sendData == null || _sendByteCounter >= _sendData?.size ?: 0) {
            _sendByteCounter = 0
            _sendData = null
            return
        }

        val rxService: BluetoothGattService = _bluetoothGatt?.getService(RX_SERVICE_UUID) ?: return
        val rxChar = rxService.getCharacteristic(RX_CHAR_UUID)
        val startIndex = _sendByteCounter
        val endIndex = _sendByteCounter + 20.coerceAtMost(_sendData?.size ?: 0)
        val curData = _sendData?.copyOfRange(startIndex, endIndex)

        _sendByteCounter = endIndex + 1
        rxChar.value = curData

        val status: Boolean = _bluetoothGatt?.writeCharacteristic(rxChar) ?: false
    }
}
