package com.har.habitforyou.ui.printer.tab.settings

import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.databinding.ItemBluetoothScannedBinding

class BluetoothScannedListItemViewHolder(private val binding: ItemBluetoothScannedBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: BluetoothScannedListItemViewModel) {
        binding.viewModel = viewModel
    }
}