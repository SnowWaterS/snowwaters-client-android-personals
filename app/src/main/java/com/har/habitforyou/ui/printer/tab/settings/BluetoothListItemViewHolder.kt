package com.har.habitforyou.ui.printer.tab.settings

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.base.ItemSelectListener
import com.har.habitforyou.databinding.ItemBluetoothScannedBinding

class BluetoothListItemViewHolder(private val binding: ItemBluetoothScannedBinding):
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var deviceSelectListener: ItemSelectListener

    fun bind(viewModel: BluetoothListItemViewModel, deviceSelectListener: ItemSelectListener) {
        binding.viewModel = viewModel
        binding.root.setOnClickListener(this)
        this.deviceSelectListener = deviceSelectListener
    }

    override fun onClick(v: View) {
        deviceSelectListener.onSelect(v, adapterPosition)
    }
}
