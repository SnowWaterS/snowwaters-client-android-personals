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


    private val REQUEST_ENABLE_BT = 3
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mDevices: Set<BluetoothDevice>? = null
    var mPairedDeviceCount = 0
    var mRemoteDevice: BluetoothDevice? = null
    var mSocket: BluetoothSocket? = null
    var mInputStream: InputStream? = null
    var mOutputStream: OutputStream? = null
    var mWorkerThread: Thread? = null
    var readBufferPositon //버퍼 내 수신 문자 저장 위치
            = 0
    var readBuffer: ByteArray = byteArrayOf()
    var mDelimiter: Byte = 10

    private val bluetoothScanBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.d(TAG, "블루투스 기기 발견!")
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
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

    fun connectBluetoothDevices(address: String) {
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

        // 페어링
        val curDevice = scannedBluetoothDevices.first()
        val createBoneMethod = curDevice.javaClass.getMethod("createBond", null)
        createBoneMethod.invoke(curDevice, null)

        val pairingPin = "0000"
        curDevice.setPin( pairingPin.toByteArray() )
        curDevice.createBond()

//        // 페어링 끊기
//        val removeBondMethod = curDevice.javaClass.getMethod("removeBond", null)
//        removeBondMethod.invoke(curDevice, null)
//
//        curDevice.removeBond()


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