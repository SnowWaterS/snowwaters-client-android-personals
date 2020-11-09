package com.har.habitforyou.ui.printer.tab.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.har.habitforyou.databinding.ItemBluetoothScannedBinding

class BluetoothScannedListAdapter: RecyclerView.Adapter<BluetoothScannedListItemViewHolder>() {

    val viewModels: MutableList<BluetoothScannedListItemViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothScannedListItemViewHolder {
        val binding = ItemBluetoothScannedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BluetoothScannedListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return viewModels.size
    }

    override fun onBindViewHolder(holder: BluetoothScannedListItemViewHolder, position: Int) {
        val viewModel = viewModels[position]
        holder.bind(viewModel)
    }

    fun setViewModels(viewModels: List<BluetoothScannedListItemViewModel>) {
        this.viewModels.clear()
        if (viewModels.isNotEmpty()) {
            this.viewModels.addAll(viewModels)
        }

        notifyDataSetChanged()
    }
}