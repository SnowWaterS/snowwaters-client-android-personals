package com.har.habitforyou.util

import android.bluetooth.BluetoothDevice
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.ui.printer.tab.settings.BluetoothScannedListAdapter
import com.har.habitforyou.ui.printer.tab.settings.BluetoothScannedListItemViewModel

object BindingUtil {

        @JvmStatic
        @BindingAdapter("scannedList")
        fun getScannedList(recyclerView: RecyclerView, devicesList: List<BluetoothDevice>) {
            val adapter = recyclerView.adapter as? BluetoothScannedListAdapter ?: return
            val viewModels: MutableList<BluetoothScannedListItemViewModel> = mutableListOf()
            devicesList.forEach {
                viewModels.add(BluetoothScannedListItemViewModel(it))
            }
            adapter.setViewModels(viewModels)
        }
 }