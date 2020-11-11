package com.har.habitforyou.util

import android.bluetooth.BluetoothDevice
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.ui.printer.tab.settings.BluetoothListAdapter
import com.har.habitforyou.ui.printer.tab.settings.BluetoothListItemViewModel

object BindingUtil {

        @JvmStatic
        @BindingAdapter("bluetoothDeviceList")
        fun getScannedList(recyclerView: RecyclerView, devicesList: List<BluetoothDevice>) {
            val adapter = recyclerView.adapter as? BluetoothListAdapter ?: return
            val viewModels: MutableList<BluetoothListItemViewModel> = mutableListOf()
            devicesList.forEach {
                viewModels.add(BluetoothListItemViewModel(it))
            }
            adapter.setViewModels(viewModels)
        }
 }