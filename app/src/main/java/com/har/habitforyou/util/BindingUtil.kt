package com.har.habitforyou.util

import android.bluetooth.BluetoothDevice
import android.view.View
import android.widget.TextView
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

    @JvmStatic
    @BindingAdapter("stringRes")
    fun getStringRes(view: View, resId: Int) {
        if (resId > 0) {
            if (view is TextView) {
                view.setText(resId)
            }
        }
    }
}