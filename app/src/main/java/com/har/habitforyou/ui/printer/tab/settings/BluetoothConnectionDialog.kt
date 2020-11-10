package com.har.habitforyou.ui.printer.tab.settings

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.R
import com.har.habitforyou.base.BaseBindingDialog
import com.har.habitforyou.databinding.DialogBluetoothConnectionBinding

class BluetoothConnectionDialog
    : BaseBindingDialog<DialogBluetoothConnectionBinding, BluetoothConnectionDialogViewModel>(){

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

        val adapter = BluetoothScannedListAdapter()
        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            private fun checkEmpty() {
                viewModel.setScannedListVisibilty(adapter.itemCount != 0)
            }
        })

        val layoutManagerForExisting = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        layoutManagerForExisting.isItemPrefetchEnabled = true
        binding.rvScannedList.adapter = adapter
        binding.rvScannedList.layoutManager = layoutManagerForExisting
        binding.rvScannedList.itemAnimator = null

        initBluetoothConnectionDialog()
    }

    private fun initBluetoothConnectionDialog() {
        getViewModel().setScannedDeviceList()
    }

    companion object {
        @JvmStatic
        fun instance(): BluetoothConnectionDialog {
            return BluetoothConnectionDialog()
        }
    }

}