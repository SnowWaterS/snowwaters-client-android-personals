package com.har.habitforyou.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import com.har.habitforyou.`object`.Result
import com.har.habitforyou.`object`.ResultCallback
import com.har.habitforyou.constant.BluetoothUUIDConstant
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class BluetoothUtil {

    companion object {

        private const val TAG = "BluetoothUtil"

        @get:Synchronized
        var instance: BluetoothUtil? = null
            get() = field ?: BluetoothUtil()
    }

    private val scannedBluetoothSet: MutableSet<Pair<String, String>> = mutableSetOf()
    private val scannedBluetoothDevices: MutableSet<BluetoothDevice> = mutableSetOf()

    private var resultCallback: ResultCallback<Set<BluetoothDevice>>? = null

    private val REQUEST_ENABLE_BT = 3
    var btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    var mSocket: BluetoothSocket? = null
    var mInputStream: InputStream? = null
    var mOutputStream: OutputStream? = null

    var mPairedDeviceCount = 0
    var mRemoteDevice: BluetoothDevice? = null
    var mWorkerThread: Thread? = null
    var readBufferPositon = 0 //버퍼 내 수신 문자 저장 위치
    var readBuffer: ByteArray = byteArrayOf()
    var mDelimiter: Byte = 10

    private val bluetoothScanBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        val deviceName = it.name
                        val deviceHardwareAddress = it.address
                        scannedBluetoothSet.add(Pair(deviceName, deviceHardwareAddress))
                        scannedBluetoothDevices.add(it)
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d(TAG, "Bluetooth 스캔 시작")
                    scannedBluetoothSet.clear()
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d(TAG, "Bluetooth 스캔 완료")
                     unregisterBroadcastReceiver()
                    resultCallback?.onResult(Result.createSuccessResult(scannedBluetoothDevices))
                }
            }
        }
    }

    fun getPairedDevices(): Set<BluetoothDevice>? {
        return btAdapter?.bondedDevices
    }

    fun getScannedDevices(callback: ResultCallback<Set<BluetoothDevice>>) {
        Log.d(TAG, "getScannedBluetoothDevices")

        val context = ContextUtil.appContext?.applicationContext
        resultCallback = callback

        if (btAdapter == null) {
            Toast.makeText(context, "블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show()
            resultCallback?.onResult(Result.createResult(-1, "블루투스를 지원하지 않습니다.", setOf()))
            return
        }

        if (btAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            Toast.makeText(context, "블루투스를 활성화 시켜주세요.", Toast.LENGTH_LONG).show()
            resultCallback?.onResult(Result.createResult(-1, "블루투스를 사용할 수 없습니다.", setOf()))
            return
        }

        registerBroadcastReceiver()
        val startScanningResult = btAdapter?.startDiscovery() ?: false
        if (!startScanningResult) {
            resultCallback?.onResult(Result.createResult(-1, "블루투스 스캔을 시작할 수 없습니다..", setOf()))
            return
        }
    }

    fun bindBluetoothDevices(bluetoothDevice: BluetoothDevice) {
        Log.d(TAG, "bindBluetoothDevices")
//        // 페어링 하기
//        val createBoneMethod = bluetoothDevice.javaClass.getMethod("createBond", null)
//        createBoneMethod.invoke(bluetoothDevice, null)

        val pairingPin = "0000"
        bluetoothDevice.setPin( pairingPin.toByteArray() )
        val bondResult = bluetoothDevice.createBond()
        Log.d(TAG, "bindBluetoothDevices: $bondResult")

    }

    fun unbindBluetoothDevices(bluetoothDevice: BluetoothDevice) {
        // 페어링 끊기
        val removeBondMethod = bluetoothDevice.javaClass.getMethod("removeBond", null)
        removeBondMethod.invoke(bluetoothDevice, null)

    }

    fun connectBluetoothDevices(address: String) {
        Log.d(TAG, "connectBluetoothDevices")
        val context = ContextUtil.appContext?.applicationContext
        val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter == null) {
            Toast.makeText(context, "블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show()
            return
        }

        if (!btAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            Toast.makeText(context, "블루투스를 활성화 시켜주세요.", Toast.LENGTH_LONG).show()
            return
        }


        val curDevice = btAdapter.getRemoteDevice(address)

        // 연결을 위한 소켓 오픈
        val uuid = UUID.fromString(BluetoothUUIDConstant.BASE_UUID)

        try {
            mSocket = curDevice.createRfcommSocketToServiceRecord(uuid)
            mSocket?.connect()

            mOutputStream = mSocket?.outputStream
            mInputStream = mSocket?.inputStream



        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnectBluetoothDevices() {

    }

    private fun registerBroadcastReceiver() {
        Log.d(TAG, "블루투스 리시버 등록")
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        val context = ContextUtil.appContext?.applicationContext
        context?.registerReceiver(bluetoothScanBroadcastReceiver, filter)
    }

    private fun unregisterBroadcastReceiver() {
        scannedBluetoothSet.forEach {
            Log.d(TAG, "Scanned Devices: ${it.first} + ${it.second}")
        }
        val context = ContextUtil.appContext?.applicationContext
        context?.unregisterReceiver(bluetoothScanBroadcastReceiver)
    }

}