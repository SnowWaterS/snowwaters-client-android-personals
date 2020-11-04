package com.har.habitforyou.ui.settings

import android.util.Log
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
    }

    companion object {
        @JvmStatic
        fun instance(): BluetoothConnectionDialog {
            return BluetoothConnectionDialog()
        }
    }

}