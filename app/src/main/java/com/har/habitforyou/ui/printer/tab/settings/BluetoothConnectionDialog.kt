package com.har.habitforyou.ui.printer.tab.settings

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.R
import com.har.habitforyou.base.BaseBindingDialog
import com.har.habitforyou.databinding.DialogBluetoothConnectionBinding

class BluetoothConnectionDialog :
    BaseBindingDialog<DialogBluetoothConnectionBinding, BluetoothConnectionDialogViewModel>(),
    BluetoothSelectListener {

    override fun getContentLayoutId(): Int {
        return R.layout.dialog_bluetooth_connection
    }

    override fun getViewModelClass(): Class<BluetoothConnectionDialogViewModel> {
        return BluetoothConnectionDialogViewModel::class.java
    }

    override fun initBinding(
        viewModel: BluetoothConnectionDialogViewModel,
        binding: DialogBluetoothConnectionBinding
    ) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val pairedListAdapter = BluetoothListAdapter()
        pairedListAdapter.setBluetoothSelectListener(this)
        pairedListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            private fun checkEmpty() {
                viewModel.setPairedListVisibilty(pairedListAdapter.itemCount != 0)
            }
        })

        val layoutManagerForPaired = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        layoutManagerForPaired.isItemPrefetchEnabled = true
        binding.rvPairedList.adapter = pairedListAdapter
        binding.rvPairedList.layoutManager = layoutManagerForPaired
        binding.rvPairedList.itemAnimator = null


        val scannedListAdapter = BluetoothListAdapter()
        scannedListAdapter.setBluetoothSelectListener(this)
        scannedListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            private fun checkEmpty() {
                viewModel.setScannedListVisibilty(scannedListAdapter.itemCount != 0)
            }
        })

        val layoutManagerForScanned = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        layoutManagerForScanned.isItemPrefetchEnabled = true
        binding.rvScannedList.adapter = scannedListAdapter
        binding.rvScannedList.layoutManager = layoutManagerForScanned
        binding.rvScannedList.itemAnimator = null

        initBluetoothConnectionDialog()
    }

    private fun initBluetoothConnectionDialog() {
        getViewModel().showLoading()
        getViewModel().setScannedDeviceList()
    }

    companion object {
        @JvmStatic
        fun instance(): BluetoothConnectionDialog {
            return BluetoothConnectionDialog()
        }
    }

    override fun onSelect(position: Int, bluetoothDevice: BluetoothDevice) {
        Log.d("Bluetooth", "postion: $position, device: ${bluetoothDevice.name}")
    }

}